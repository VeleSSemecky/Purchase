package com.veles.purchase.domain.usecase.scanner

import com.veles.purchase.domain.model.scanner.ScannedProduct

class ParsePriceTagUseCase {

    operator fun invoke(lines: List<String>): ScannedProduct {
        val priceRegex = """\d+[.,]\d{2}""".toRegex()
        var price = ""
        var currency = ""
        var name = ""

        for (line in lines) {
            val trimmed = line.trim()
            val match = priceRegex.find(trimmed)
            if (match != null && price.isEmpty()) {
                price = match.value.replace(",", ".")
                currency = when {
                    trimmed.contains("грн", ignoreCase = true) ||
                    trimmed.contains("uah", ignoreCase = true) -> "UAH"
                    trimmed.contains("PLN", ignoreCase = true) ||
                    trimmed.contains("zł", ignoreCase = true) -> "PLN"
                    trimmed.contains("USD", ignoreCase = true) ||
                    trimmed.contains("$") -> "USD"
                    trimmed.contains("EUR", ignoreCase = true) ||
                    trimmed.contains("€") -> "EUR"
                    else -> ""
                }
            } else if (trimmed.length > 3 && name.isEmpty() && !trimmed.matches("[0-9.,\\s]+".toRegex())) {
                name = trimmed
            }
        }

        return ScannedProduct(name = name, price = price, currency = currency)
    }
}
