package com.veles.purchase.platform.extractor

/**
 * iOS fallback: extracts price-like numbers using regex.
 * Returns MoneyAnnotation list so ParsePriceTagUseCase can apply the same
 * context-aware selection logic (Cena: vs Cena/kg:) as on Android.
 */
class IosPriceEntityExtractor : PriceEntityExtractor {

    // Matches XX,XX or XX.XX — NOT followed by another digit (avoids weight 0,470)
    private val priceRegex = """\d+[.,]\d{2}(?!\d)""".toRegex()

    override suspend fun extractMoneyEntities(text: String): List<MoneyAnnotation> {
        return priceRegex.findAll(text).mapNotNull { match ->
            val amount = match.value.replace(",", ".").toDoubleOrNull() ?: return@mapNotNull null
            MoneyAnnotation(
                text = match.value,
                start = match.range.first,
                end = match.range.last + 1,
                amount = amount,
                currency = detectCurrency(text, match.range.last)
            )
        }.toList()
    }

    private fun detectCurrency(text: String, afterIndex: Int): String {
        val snippet = text.substring(afterIndex.coerceAtMost(text.length - 1))
            .take(20)
            .uppercase()
        return when {
            "UAH" in snippet || "ГРН" in snippet -> "UAH"
            "PLN" in snippet || "ZŁ" in snippet || "ZT" in snippet -> "PLN"
            "USD" in snippet || "$" in snippet -> "USD"
            "EUR" in snippet || "€" in snippet -> "EUR"
            "CZK" in snippet || "KČ" in snippet -> "CZK"
            else -> ""
        }
    }
}
