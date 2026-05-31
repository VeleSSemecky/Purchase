package com.veles.purchase.domain.usecase.scanner

import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.domain.model.scanner.ReceiptItem

/**
 * Parses OCR lines from a store receipt.
 *
 * Strategy A — Split VAT (Polish standard):
 *   "DUCK SHOYU .B"        name on its own line, VAT code suffix
 *   "1 x49,00 49,00B"      qty × unit total on separate line
 *
 * Strategy B — Mixed-line (OCR merges name + qty×price):
 *   "TANTANMEN CLASSIC.B1 x50,00 50,00B"   name+VAT immediately followed by qty×price
 *
 * Strategy C — Inline (name and price separated by whitespace):
 *   "Product name   12,99"
 *
 * Strategy D — Euro column (Spanish/Western format):
 *   Names on early lines, "N €price" rows later
 *   "€3.00"  or  "1 €3.00"
 *
 * Total detection: keyword line → scan forward for maximum price; fallback = overall max.
 */
class ParseReceiptUseCase {

    // Comma-or-dot decimal, 1–6 digits (word boundary avoids matching dates)
    private val priceRegex = Regex("""\b(\d{1,6}[.,]\d{2})\b""")

    // Euro prefix price: €3.00 or €12.00 (dot as decimal)
    private val euroPriceRegex = Regex("""€\s*(\d{1,6}(?:[.,]\d{2})?)""")

    private val currencyRegex = Regex("""zł|PLN|грн|UAH|USD|\$|€|EUR""", RegexOption.IGNORE_CASE)

    private val totalKeywords = listOf(
        "razem", "suma", "total", "sum", "до сплати", "разом", "підсумок",
        "do zapłaty", "kwota", "płatność", "zapłać", "payment",
        "należność", "łącznie", "suma pln", "suma zł",
        "cantidad a pagar", "importe", "total cant", "importe total"
    )

    private val skipKeywords = listOf(
        "paragon", "receipt", "nip", "tel:", "www.", "http",
        "sklep", "kasa", "kasjer", "dziękujemy", "zapraszamy",
        "vat", "ptu", "stawka", "podatek", "sprzedaz", "opodatkowana",
        "drukarka", "rozliczenie", "karta", "gotówka", "reszta",
        "ulica", "ul.", "al.", " sp. z", "numer", "nr sys", "nr:",
        "bdo:", "paragon fiskalny", "piragon", "fiskalny",
        "impuesto", "iva", "precio", "empleado", "dispositivo",
        "comensales", "porcentaje", "manager", "parcial", "mesa",
        "cif/nif", "till", "recibo", "pedido", "copia"
    )

    // VAT suffix at end of line: ".A", ".B", " A", ",B" etc.
    private val vatSuffixRegex = Regex("""\s*[., ][ABCDE]\s*$""", RegexOption.IGNORE_CASE)

    // Standard: "1 x49,00 49,00B" or "2 x15.50 31,00A"
    private val qtyPriceRegex = Regex("""^\d+\s*[xX×]\s*\d+[.,]\s*\d{2}\s+(\d+[.,]\d{2})[A-Za-z]?\s*$""")

    // Mixed: "TANTANMEN CLASSIC.B1 x50,00 50,00B" — name before VAT+qty block
    private val mixedLineRegex = Regex(
        """^(.{2,50}?)\s*[.,][ABCDE]\s*\d+\s*[xX×].*?(\d+[.,]\s*\d{2})[ABCDE]?\s*$""",
        RegexOption.IGNORE_CASE
    )

    // Euro column: "1 €3.00" or "2 €16.00"
    private val euroQtyPriceRegex = Regex("""^\d+\s*€\s*(\d{1,6}[.,]\d{2})\s*$""")

    operator fun invoke(lines: List<String>): ReceiptData {
        val cleaned = lines.map { it.trim() }.filter { it.isNotEmpty() }
        val currency = detectCurrency(cleaned)
        val total = extractTotal(cleaned)
        val items = extractItems(cleaned)
        return ReceiptData(totalAmount = total, currency = currency, items = items)
    }

    private fun detectCurrency(lines: List<String>): String {
        for (line in lines) {
                val m = currencyRegex.find(line) ?: continue
                return when (m.value.lowercase()) {
                    "zł", "pln" -> "PLN"
                    "грн", "uah" -> "UAH"
                    "usd", "$" -> "USD"
                    "€", "eur" -> "EUR"
                    else -> m.value.uppercase()
                }
        }
        return ""
    }

    private fun extractTotal(lines: List<String>): Double? {
        val keywordIndex = lines.indexOfFirst { line ->
                val lower = line.lowercase()
                totalKeywords.any { lower.contains(it) }
        }

        if (keywordIndex >= 0) {
                val kwLine = lines[keywordIndex]
                val onSameLine = allPricesIn(kwLine).maxOrNull()
                if (onSameLine != null) return onSameLine

                val afterKeyword = lines.drop(keywordIndex + 1).flatMap { allPricesIn(it) }
                if (afterKeyword.isNotEmpty()) return afterKeyword.max()
        }

        return lines.flatMap { allPricesIn(it) }.maxOrNull()
    }

    /** Extract all prices from a line, supporting both comma-decimal and € prefix */
    private fun allPricesIn(line: String): List<Double> {
        val standard = priceRegex.findAll(line)
                .mapNotNull { it.groupValues[1].replace(",", ".").toDoubleOrNull() }
                .filter { it >= 0.01 }
        val euro = euroPriceRegex.findAll(line)
                .mapNotNull { it.groupValues[1].replace(",", ".").toDoubleOrNull() }
                .filter { it >= 0.01 }
        return (standard + euro).toList()
    }

    private fun extractItems(lines: List<String>): List<ReceiptItem> {

        // ── Strategy B: mixed-line (name.VAT + qty×price on same line) ─────────
        val mixedItems = mutableListOf<ReceiptItem>()
        for (line in lines) {
                if (isSkipLine(line)) continue
                val m = mixedLineRegex.find(line) ?: continue
                val name = m.groupValues[1].trim()
                val price = m.groupValues[2].replace(" ", "").replace(",", ".").toDoubleOrNull() ?: continue
                if (name.length >= 2 && !isNoiseLine(name) && price > 0) {
                    mixedItems.add(ReceiptItem(name, price))
                }
        }
        if (mixedItems.isNotEmpty()) return mixedItems

        // ── Strategy A: split VAT lines ──────────────────────────────────────
        val vatNames = mutableListOf<String>()
        val qtyPrices = mutableListOf<Double>()

        for (line in lines) {
                if (isSkipLine(line)) continue

                val qtyMatch = qtyPriceRegex.find(line)
                if (qtyMatch != null) {
                    val price = qtyMatch.groupValues[1].replace(",", ".").toDoubleOrNull()
                    if (price != null && price > 0) { qtyPrices.add(price); continue }
                }

                if (vatSuffixRegex.containsMatchIn(line)) {
                    val name = line.replace(vatSuffixRegex, "").trim()
                    if (name.length >= 2 && !isNoiseLine(name)) { vatNames.add(name); continue }
                }
        }

        if (vatNames.isNotEmpty() && qtyPrices.isNotEmpty()) {
                val count = minOf(vatNames.size, qtyPrices.size)
                return (0 until count).map { i -> ReceiptItem(vatNames[i], qtyPrices[i]) }
        }

        // ── Strategy D: Euro column ───────────────────────────────────────────
        // Collect product names (lines with no price) and euro prices in order
        val euroNames = mutableListOf<String>()
        val euroPrices = mutableListOf<Double>()
        for (line in lines) {
                if (isSkipLine(line)) continue
                if (isDateLine(line)) continue
                // "1 €3.00" or "2 €16.00"
                val euroQtyMatch = euroQtyPriceRegex.find(line)
                if (euroQtyMatch != null) {
                    val price = euroQtyMatch.groupValues[1].replace(",", ".").toDoubleOrNull()
                    if (price != null && price > 0) { euroPrices.add(price); continue }
                }
                // "€3.00" standalone price line
                val euroMatch = euroPriceRegex.find(line)
                if (euroMatch != null && line.trim().matches(Regex("""€\s*\d+[.,]\d{2}"""))) {
                    val price = euroMatch.groupValues[1].replace(",", ".").toDoubleOrNull()
                    if (price != null && price > 0) { euroPrices.add(price); continue }
                }
                // Potential product name (no digits, reasonable length)
                if (euroPrices.isEmpty() && allPricesIn(line).isEmpty() &&
                    line.length in 3..60 && !isNoiseLine(line)
                ) {
                    euroNames.add(line.trim())
                }
        }
        if (euroPrices.isNotEmpty() && euroNames.isNotEmpty()) {
                val count = minOf(euroNames.size, euroPrices.size)
                return (0 until count).map { i -> ReceiptItem(euroNames[i], euroPrices[i]) }
        }

        // ── Strategy C: inline format "Product name   12,99" ─────────────────
        val inlineRegex1 = Regex("""^(.{2,50}?)\s{2,}(\d{1,5}[.,]\d{2})(?:\s*[A-E])?\s*$""")
        val inlineRegex2 = Regex("""^(.{2,50}?)\s+(\d{1,5}[.,]\d{2})\s*$""")
        val fallbackItems = mutableListOf<ReceiptItem>()
        for (line in lines) {
                if (isSkipLine(line)) continue
                if (isDateLine(line)) continue
                val m = inlineRegex1.find(line) ?: inlineRegex2.find(line) ?: continue
                val name = m.groupValues[1].trim()
                val price = m.groupValues[2].replace(",", ".").toDoubleOrNull() ?: continue
                if (name.length < 2 || isNoiseLine(name) || price < 0.01) continue
                fallbackItems.add(ReceiptItem(name, price))
        }
        return fallbackItems
    }

    private fun isSkipLine(line: String): Boolean {
        val lower = line.lowercase()
        return totalKeywords.any { lower.contains(it) } ||
                skipKeywords.any { lower.contains(it) }
    }

    private fun isNoiseLine(text: String): Boolean {
        val lower = text.lowercase()
        return skipKeywords.any { lower.contains(it) } ||
                totalKeywords.any { lower.contains(it) } ||
                text.all { it.isDigit() || it == ',' || it == '.' || it == ' ' }
    }

    private fun isDateLine(line: String): Boolean =
        line.contains(Regex("""\d{4}-\d{2}-\d{2}""")) ||
        line.contains(Regex("""\d{2}[./]\d{2}[./]\d{2,4}"""))
}
