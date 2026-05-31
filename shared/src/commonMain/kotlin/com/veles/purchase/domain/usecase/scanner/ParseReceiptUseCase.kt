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

    private val priceRegex = Regex("""\d+[.,]\d{2}(?![.\d])""")
    private val currencyRegex = Regex("""zł|PLN|грн|UAH|USD|\$|€|EUR""", RegexOption.IGNORE_CASE)

    private val totalKeywords = listOf(
        "razem", "suma", "total", "sum", "до сплати", "разом", "підсумок",
        "do zapłaty", "kwota", "płatność", "zapłać", "payment"
    )

    operator fun invoke(lines: List<String>): ReceiptData {
        val cleanedLines = lines.map { it.trim() }.filter { it.isNotEmpty() }

        val currency = detectCurrency(cleanedLines)
        val items = extractItems(cleanedLines)
        val total = extractTotal(cleanedLines)

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
        // Look for lines containing total keywords — take the last/biggest matching price
        val candidates = mutableListOf<Double>()
        for (line in lines) {
            val lower = line.lowercase()
            if (totalKeywords.any { lower.contains(it) }) {
                val match = priceRegex.find(line) ?: continue
                val value = match.value.replace(",", ".").toDoubleOrNull() ?: continue
                candidates.add(value)
            }
        }
        // Return the largest candidate (total is usually the biggest number on a TOTAL line)
        return candidates.maxOrNull()
    }

    private fun extractItems(lines: List<String>): List<ReceiptItem> {
        val items = mutableListOf<ReceiptItem>()
        // Typical receipt line: "Product name   12,99" or "Product name  x2  25,98"
        // We look for lines with a price at the end and some text at the start
        val itemLineRegex = Regex("""^(.{2,40}?)\s+(\d+[.,]\d{2})(?![.\d])\s*$""")

        for (line in lines) {
            val lower = line.lowercase()
            // Skip header/footer/total lines
            if (totalKeywords.any { lower.contains(it) }) continue
            if (lower.contains("paragon") || lower.contains("receipt") || lower.contains("nip")
                || lower.contains("tel") || lower.contains("www") || lower.contains("http")) continue

            val match = itemLineRegex.find(line) ?: continue
            val name = match.groupValues[1].trim()
            val price = match.groupValues[2].replace(",", ".").toDoubleOrNull() ?: continue

            // Skip lines with very short names (likely noise) or very low prices (tax rows, etc.)
            if (name.length < 2) continue

            items.add(ReceiptItem(name = name, price = price))
        }
        return items
    }
}
