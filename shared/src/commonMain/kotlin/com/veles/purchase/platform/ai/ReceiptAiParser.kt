package com.veles.purchase.platform.ai

import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.domain.model.scanner.ReceiptItem

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
    /** Returns the engine type of this implementation. */
    val engineType: EngineType

    /** Returns true if this engine is available/supported on this device. */
    suspend fun isAvailable(): Boolean

    /**
     * Parses a receipt image using the AI model.
     * Returns null if parsing fails or engine is unavailable.
     */
    suspend fun parse(imageBytes: ByteArray): ReceiptData?

    enum class EngineType {
        GEMINI_NANO,
        GROQ_CLOUD,
        LOCAL_SLM,
        OCR_TEXT_MODEL
    }
}

// ── Shared prompt engineering ─────────────────────────────────────────────────

internal object ReceiptPromptBuilder {
    private const val OCR_TEXT_MAX_CHARS = 3_500

    /** Prompt for vision models (Gemini Nano / Groq) — image is supplied separately. */
    fun buildPrompt(): String = """
        You are a receipt parser. Extract ALL purchased items from this receipt image.

        For each item line extract:
        - "name": the item name exactly as printed
        - "price": the TOTAL amount charged for that line (row total = unit price × quantity)
          This is typically the LAST / RIGHTMOST number on each item line.

        SKIP: tax, VAT, IVA, subtotal, total, tip, discounts, service charge, payment method lines, column headers.
        "total": the final amount paid (grand total on the receipt).
        Detect currency from the symbol (€=EUR, $=USD, £=GBP, ₴=UAH, etc.).
        Copy item names exactly as printed — any language is fine.

        Return ONLY a raw JSON object (no markdown, no explanation):
        {"total": number, "currency": "EUR", "items": [{"name": "...", "price": number}]}
    """.trimIndent()

    /**
     * Builds a prompt using the pipe-separated column matrix from [YCoordinateTextRecognizer].
     *
     * Each row shows visual columns in left-to-right order, e.g.:
     *   "CocaCola 0.2 | €3.00 | 1 | €3.00"
     *   "New York Sour | €8.00 | 2 | €16.00"
     *   "Parcial | €140.50"
     *
     * The model decides semantics (name / qty / price / total) — no language assumptions here.
     *
     * Rows are produced by YCoordinateTextRecognizer in androidMain.
     */
    fun buildOcrTextPrompt(lines: List<String>): String {
        val alignedText = lines.toOcrPromptText()
        if (alignedText.isBlank()) return ""

        return """
            Below is OCR output from a receipt. Columns within each row are separated by " | " in left-to-right order.

            How to read columns:
            - Product rows typically have: NAME | [unit_price] | [qty] | LINE_TOTAL
              The LAST column of a product row is the line total (qty × unit price). Use that as the item price.
            - Summary rows typically have: [label] | AMOUNT
              Look for the row whose amount equals (or is close to) the sum of all product line totals — that is the grand total.
            - Single-column rows are usually headers, addresses, or footers — skip them.
            - Detect currency from symbols anywhere in the text: €→EUR  $→USD  £→GBP  ₴→UAH

            RECEIPT:
            $alignedText

            Return ONLY raw JSON (no markdown, no explanation):
            {"total":number|null,"currency":"","items":[{"name":"string","price":number}]}
        """.trimIndent()
    }

    fun isOcrTextTruncated(lines: List<String>): Boolean {
        val fullText = lines
            .asSequence()
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .joinToString("\n")
        return fullText.length > OCR_TEXT_MAX_CHARS
    }

    private fun List<String>.toOcrPromptText(): String = asSequence()
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .joinToString("\n")
        .take(OCR_TEXT_MAX_CHARS)
        .trim()
}

internal object ReceiptResponseParser {
    private val totalRegex = Regex(""""total"\s*:\s*(?:"?([0-9]+(?:[.,][0-9]+)?)"?|null)""")
    private val currencyRegex = Regex(""""currency"\s*:\s*"([^"]*)""")
    // Handles both {"name":"x","price":9.50} and {"price":9.50,"name":"x"}; price may be quoted and use comma decimals.
    private val itemObjectRegex = Regex("""\{[^{}]*"name"\s*:\s*"[^"]+"[^{}]*"price"\s*:\s*"?[0-9]+(?:[.,][0-9]+)?"?[^{}]*}|\{[^{}]*"price"\s*:\s*"?[0-9]+(?:[.,][0-9]+)?"?[^{}]*"name"\s*:\s*"[^"]+"[^{}]*}""")
    private val nameRegex = Regex(""""name"\s*:\s*"([^"]+)""")
    private val priceRegex = Regex(""""price"\s*:\s*"?([0-9]+(?:[.,][0-9]+)?)"?""")

    fun parse(json: String, fallbackCurrency: String = ""): ReceiptData? {
        return try {
            val total = totalRegex.find(json)?.groupValues?.getOrNull(1)?.replace(',', '.')?.toDoubleOrNull()
            val currency = currencyRegex.find(json)?.groupValues?.getOrNull(1) ?: fallbackCurrency
            val items = itemObjectRegex.findAll(json).mapNotNull { objectMatch ->
                val objectJson = objectMatch.value
                val name = nameRegex.find(objectJson)?.groupValues?.getOrNull(1)?.trim().orEmpty()
                val price = priceRegex.find(objectJson)?.groupValues?.getOrNull(1)?.replace(',', '.')?.toDoubleOrNull()
                if (name.isBlank() || price == null || price <= 0.0) null
                else ReceiptItem(name = name, price = price)
            }.toList()

            if (items.isEmpty() && total == null) null
            else ReceiptData(
                totalAmount = total,
                currency = currency,
                items = items
            )
        } catch (_: Exception) {
            null
        }
    }
}
