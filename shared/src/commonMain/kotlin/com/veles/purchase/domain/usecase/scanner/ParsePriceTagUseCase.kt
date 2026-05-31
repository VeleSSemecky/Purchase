package com.veles.purchase.domain.usecase.scanner

import com.veles.purchase.domain.model.scanner.ScannedProduct

class ParsePriceTagUseCase {

    operator fun invoke(lines: List<String>): ScannedProduct {
        val priceRegex = """\d+[.,]\d{2}""".toRegex()

        // Patterns that indicate unit price (price per kg/l/piece) — skip these
        val unitPricePattern = Regex("""(/kg|per kg|/кг|na kg|/l\b|/liter|/litr|/szt|cena/kg|ціна/кг)""", RegexOption.IGNORE_CASE)
        // Pattern for "Cena:" (total price label without /kg)
        val totalPricePattern = Regex("""^cena\s*:""", RegexOption.IGNORE_CASE)

        var price = ""
        var currency = ""
        var name = ""

        // Pass 1: look for a line that says "Cena: XX,XX" (total price)
        for (line in lines) {
            val trimmed = line.trim()
            if (totalPricePattern.containsMatchIn(trimmed)) {
                val match = priceRegex.find(trimmed)
                if (match != null) {
                    price = match.value.replace(",", ".")
                    currency = detectCurrency(trimmed)
                    break
                }
            }
        }

        // Pass 2: if no "Cena:" found, pick the first price NOT on a unit-price line
        if (price.isEmpty()) {
            for (line in lines) {
                val trimmed = line.trim()
                if (unitPricePattern.containsMatchIn(trimmed)) continue
                val match = priceRegex.find(trimmed)
                if (match != null) {
                    price = match.value.replace(",", ".")
                    currency = detectCurrency(trimmed)
                    break
                }
            }
        }

        // Pass 3: pick product name — prefer the longest ALL-CAPS alphabetic line
        val capsLines = lines
            .map { it.trim() }
            .filter { line ->
                line.length >= 4 &&
                line.any { it.isLetter() } &&
                line.count { it.isLetter() } > line.length / 2 &&
                line == line.uppercase() &&
                !line.contains("@") &&
                !line.matches("[0-9.,/:\\-\\s]+".toRegex())
            }
        name = capsLines.maxByOrNull { it.length } ?: ""

        // Fallback: first non-numeric, non-short line
        if (name.isEmpty()) {
            name = lines.map { it.trim() }
                .firstOrNull { line ->
                    line.length > 3 &&
                    !line.matches("[0-9.,\\s/:\\-]+".toRegex()) &&
                    line.any { it.isLetter() }
                } ?: ""
        }

        return ScannedProduct(name = name, price = price, currency = currency)
    }

    private fun detectCurrency(line: String): String = when {
        line.contains("грн", ignoreCase = true) ||
        line.contains("uah", ignoreCase = true) -> "UAH"

        line.contains("PLN", ignoreCase = true) ||
        line.contains("zł", ignoreCase = true) ||
        line.contains("zt", ignoreCase = true) ||
        line.contains("zl", ignoreCase = true) -> "PLN"

        line.contains("USD", ignoreCase = true) ||
        line.contains("$") -> "USD"

        line.contains("EUR", ignoreCase = true) ||
        line.contains("€") -> "EUR"

        line.contains("CZK", ignoreCase = true) ||
        line.contains("Kč", ignoreCase = true) -> "CZK"

        else -> ""
    }
}
