package com.veles.purchase.platform.ai

import com.veles.purchase.domain.model.scanner.ReceiptData

/**
 * Platform-specific on-device AI receipt parser.
 *
 * Android: uses ML Kit Gemini Nano Prompt API with regex fallback.
 * iOS: uses regex fallback (Core ML / Foundation Models integration planned).
 *
 * Always call [isAvailable] before using — on unsupported devices the
 * implementation silently returns null so callers fall back to regex.
 */
interface ReceiptAiParser {
    /** Returns true if on-device AI model is available on this device. */
    suspend fun isAvailable(): Boolean

    /**
     * Parses OCR lines using on-device AI.
     * Returns null if AI is unavailable or parsing fails — caller must fall back to regex.
     */
    suspend fun parse(lines: List<String>): ReceiptData?
}

// ── Shared prompt engineering ─────────────────────────────────────────────────

internal object ReceiptPromptBuilder {
    fun buildPrompt(lines: List<String>): String {
        val text = lines.joinToString("\n")
        return """
            You are a receipt parser. Extract structured data from the receipt text below.
            
            Reply ONLY with JSON in exactly this format (no markdown, no explanation):
            {"total":124.00,"currency":"PLN","items":[{"name":"DUCK SHOYU","price":49.00},{"name":"COCA COLA","price":10.00}]}
            
            Rules:
            - total: the grand total amount paid (number)
            - currency: ISO 4217 code detected from receipt (PLN, UAH, USD, EUR, etc.)
            - items: list of purchased products with their individual prices
            - Skip header lines, address, NIP, tax summaries, and payment method lines
            - If a field cannot be determined, use null
            
            Receipt text:
            $text
        """.trimIndent()
    }
}

internal object ReceiptResponseParser {
    private val totalRegex = Regex(""""total"\s*:\s*([0-9]+(?:\.[0-9]+)?)""")
    private val currencyRegex = Regex(""""currency"\s*:\s*"([A-Z]{2,5})"""")
    private val itemRegex = Regex(""""name"\s*:\s*"([^"]+)"\s*,\s*"price"\s*:\s*([0-9]+(?:\.[0-9]+)?)""")

    fun parse(json: String, fallbackCurrency: String = ""): com.veles.purchase.domain.model.scanner.ReceiptData? {
        return try {
            val total = totalRegex.find(json)?.groupValues?.get(1)?.toDoubleOrNull()
            val currency = currencyRegex.find(json)?.groupValues?.get(1) ?: fallbackCurrency
            val items = itemRegex.findAll(json).map { m ->
                com.veles.purchase.domain.model.scanner.ReceiptItem(
                    name = m.groupValues[1],
                    price = m.groupValues[2].toDoubleOrNull() ?: 0.0
                )
            }.filter { it.price > 0 }.toList()

            if (items.isEmpty() && total == null) null
            else com.veles.purchase.domain.model.scanner.ReceiptData(
                totalAmount = total,
                currency = currency,
                items = items
            )
        } catch (_: Exception) {
            null
        }
    }
}
