package com.veles.purchase.platform.extractor

/**
 * A money annotation found in OCR text.
 * @param text  The raw text span (e.g. "20,21 Zt.")
 * @param start Character index in the full OCR string
 * @param end   Character index (exclusive) in the full OCR string
 * @param amount Normalized numeric value (e.g. 20.21)
 * @param currency Raw currency string as detected (e.g. "Zt.", "PLN", "zł")
 */
data class MoneyAnnotation(
    val text: String,
    val start: Int,
    val end: Int,
    val amount: Double,
    val currency: String
)

/**
 * Platform-specific money entity extractor.
 * Android: ML Kit Entity Extraction (on-device model).
 * iOS: regex-based fallback.
 */
interface PriceEntityExtractor {
    suspend fun extractMoneyEntities(text: String): List<MoneyAnnotation>
}
