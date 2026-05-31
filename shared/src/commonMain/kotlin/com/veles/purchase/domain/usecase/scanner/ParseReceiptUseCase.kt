package com.veles.purchase.domain.usecase.scanner

import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.domain.model.scanner.ReceiptItem

/**
 * Parses OCR lines from a store receipt to extract:
 * - Individual items (name + price)
 * - Total amount
 *
 * Supports: Polish (Razem, Suma), Ukrainian (До сплати, Разом), English (Total, Sum)
 */
class ParseReceiptUseCase {

    // Matches: 12,99 or 12.99 — not followed by more digits (to avoid matching dates like 30.05)
    private val priceRegex = Regex("""\b\d{1,5}[.,]\d{2}\b""")
    private val currencyRegex = Regex("""zł|PLN|грн|UAH|USD|\$|€|EUR""", RegexOption.IGNORE_CASE)

    private val totalKeywords = listOf(
        "razem", "suma", "total", "sum", "до сплати", "разом", "підсумок",
        "do zapłaty", "kwota", "płatność", "zapłać", "payment", "należność",
        "do zapłaty", "łącznie"
    )

    // Lines to skip — not product lines
    private val skipKeywords = listOf(
        "paragon", "receipt", "nip", "tel", "www", "http", "sklep", "kasa",
        "data", "godzina", "kasjer", "dziękujemy", "zapraszamy", "drukarka",
        "vat", "ptv", "stawka", "podatek", "opis", "ilość", "cena", "wartość",
        "numer", "nr ", "ptu"
    )

    operator fun invoke(lines: List<String>): ReceiptData {
        val cleanedLines = lines.map { it.trim() }.filter { it.isNotEmpty() }

        val currency = detectCurrency(cleanedLines)
        val total = extractTotal(cleanedLines)
        val items = if (total != null) extractItems(cleanedLines) else emptyList()

        return ReceiptData(
            totalAmount = total,
            currency = currency,
            items = items
        )
    }

    private fun detectCurrency(lines: List<String>): String {
        for (line in lines) {
            val match = currencyRegex.find(line) ?: continue
            return when (match.value.lowercase()) {
                "zł", "pln" -> "PLN"
                "грн", "uah" -> "UAH"
                "usd", "$" -> "USD"
                "€", "eur" -> "EUR"
                else -> match.value.uppercase()
            }
        }
        return ""
    }

    private fun extractTotal(lines: List<String>): Double? {
        val candidates = mutableListOf<Double>()
        for (line in lines) {
            val lower = line.lowercase()
            if (totalKeywords.any { lower.contains(it) }) {
                // Find ALL prices on this line, take the last (usually the actual total)
                val matches = priceRegex.findAll(line).toList()
                for (m in matches) {
                    val value = m.value.replace(",", ".").toDoubleOrNull() ?: continue
                    // Ignore suspiciously small values (likely quantity/tax rates)
                    if (value >= 0.01) candidates.add(value)
                }
            }
        }
        return candidates.maxOrNull()
    }

    private fun extractItems(lines: List<String>): List<ReceiptItem> {
        val items = mutableListOf<ReceiptItem>()
        // Receipt item patterns:
        // "Product name   12,99"
        // "Product name  1 x  12,99"
        // "PRODUCT NAME A  12,99 B"  (VAT code at end)
        val itemLineRegex = Regex("""^(.{2,50}?)\s{2,}(\d{1,5}[.,]\d{2})(?:\s*[ABCDEabcde])?\s*$""")
        val itemLineRegex2 = Regex("""^(.{2,50}?)\s+(\d{1,5}[.,]\d{2})\s*$""")

        for (line in lines) {
            val lower = line.lowercase()
            if (totalKeywords.any { lower.contains(it) }) continue
            if (skipKeywords.any { lower.contains(it) }) continue
            // Skip lines that look like a date (dd.mm.yyyy or dd-mm-yyyy)
            if (line.matches(Regex(""".*\d{2}[./-]\d{2}[./-]\d{4}.*"""))) continue
            // Skip lines that are only numbers/short codes
            if (line.length < 4) continue

            val match = itemLineRegex.find(line) ?: itemLineRegex2.find(line) ?: continue
            val name = match.groupValues[1].trim()
            val price = match.groupValues[2].replace(",", ".").toDoubleOrNull() ?: continue

            if (name.length < 2) continue
            // Skip if price looks like a date fragment (e.g. 30.05)
            if (price < 0.01 || price > 99999.0) continue

            items.add(ReceiptItem(name = name, price = price))
        }
        return items
    }
}
