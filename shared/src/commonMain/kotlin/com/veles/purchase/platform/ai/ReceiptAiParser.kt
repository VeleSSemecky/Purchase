package com.veles.purchase.platform.ai

import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.domain.model.scanner.ReceiptItem
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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
        OCR_TEXT_MODEL,
        OCR_GROQ
    }
}

// ── Shared prompt engineering ─────────────────────────────────────────────────

internal object ReceiptPromptBuilder {
    private const val OCR_TEXT_MAX_CHARS = 3_500

    /** Prompt for vision models (Groq / Gemini Nano / Local).
     *  Extracts name, line total price, quantity, and optional tax code.
     */
    fun buildPrompt(): String = """
        You are a precise receipt data extractor. Look at the receipt image and return JSON ONLY — no markdown, no explanation.

        EXTRACT every purchased product line with:
        - "name": product name exactly as printed (keep original language/script)
        - "price": the LINE TOTAL for this item (= unit_price × qty). Usually the LAST/RIGHTMOST price on the item line.
        - "qty": quantity as a number (default 1 if not shown). E.g. for "2 x Milk 3.00 = 6.00" → qty=2, price=6.00
        - "tax": VAT/tax code if shown on the line ("A", "B", "23%", etc.), or "" if absent

        SKIP: grand total, subtotal, VAT summary rows, discounts, service fees, payment rows, headers, store address/info.

        "total": grand total paid (the largest final amount on the receipt)
        "currency": from symbol — €→EUR, $→USD, £→GBP, ₴→UAH, zł→PLN, кн→UAH

        Numbers: always use DOT as decimal separator (e.g. 12.99 not 12,99).

        Return ONLY this JSON (no text before or after):
        {"total":number,"currency":"CODE","items":[{"name":"...","price":number,"qty":number,"tax":"..."}]}
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
            You are a professional receipt parser. Below is OCR output from a receipt. 
            Columns within each row are separated by " | " in left-to-right order.

            How to read columns:
            - Product rows: NAME | [unit_price] | [qty] | LINE_TOTAL. The LAST column is the total price for that line.
            - Summary rows: [label] | AMOUNT. Look for the grand total near "Total", "Parcial", "Importe", or "Sum".
            - Ignore headers, addresses, and tax details (IVA, VAT, TAX).
            - Detect currency from symbols (€→EUR, $→USD, £→GBP, ₴→UAH).

            Rules for JSON:
            1. Copy product names EXACTLY as printed. Correct obvious OCR typos (e.g., 'Tonlc' -> 'Tonic', 'Cala' -> 'Cola', 'Red Labe' -> 'Red Label', 'CocaCala' -> 'CocaCola').
            2. For item prices, use the LINE_TOTAL (last column). If a number is split (e.g., '7, | 00'), merge it into '7.00'.
            3. Use a DOT (.) as the decimal separator for numbers (e.g., 140.50).
            4. If currency is unknown, use an empty string.
            5. Ensure ALL purchased items are included. Do NOT skip any product lines.
            6. For "quantity", copy the qty column if present ("1", "2", "0.5 kg"), otherwise "".
            7. For "tax", copy any tax/VAT marker printed for the line ("23%", "A", "B"), otherwise "".

            Example:
            Input:
            CocaCola 0.2 | 3.00 | 1 | 3.00
            New York Sour | 8.00 | 2 | 16.00
            Total | 19.00
            Output: {"total":19.00,"currency":"EUR","items":[{"name":"CocaCola 0.2","price":3.00,"quantity":"1","tax":""},{"name":"New York Sour","price":16.00,"quantity":"2","tax":""}]}

            RECEIPT OCR DATA:
            $alignedText

            Return ONLY raw JSON:
            {"total":number|null,"currency":"ISO_CODE","items":[{"name":"string","price":number,"quantity":"string","tax":"string"}]}
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
    @Serializable
    private data class AiReceiptResponse(
        val total: Double? = null,
        val currency: String? = null,
        val items: List<AiReceiptItem> = emptyList()
    )

    @Serializable
    private data class AiReceiptItem(
        val name: String,
        val price: Double,
        /** quantity as number from the LLM (default 1.0) */
        val qty: Double = 1.0,
        /** legacy field name kept for backward-compat with OCR text prompt */
        val quantity: String = "",
        val tax: String = ""
    )

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Normalises comma-decimal numbers to dot-decimal in a raw JSON string.
     *
     * Problem: receipts use locale-specific decimal separators.
     * - EU / PL / UA style: 12,99  →  LLM echoes the receipt → invalid JSON or wrong value
     * - US / UK style:      12.99  →  already correct
     *
     * Strategy: replace `digit,digit(1-2)` patterns that are NOT followed by another digit.
     * This matches decimal cents (12,99) but avoids JSON structural commas
     * because those are always followed by whitespace or `"` or `{`, not a digit.
     *
     * Examples:
     *   `"price": 12,99`           → `"price": 12.99`   ✓
     *   `"total": 1,5`             → `"total": 1.5`     ✓
     *   `{"a":1,"b":2}`            → unchanged          ✓  (1,"b" — after comma is `"`)
     *   `[{"p":3},{"p":5}]`        → unchanged          ✓  (3},{ — no digit after comma)
     */
    private val commaDecimalRegex = Regex("""(\d),(\d{1,2})(?!\d)""")

    private fun normalizeDecimalSeparator(raw: String): String =
        commaDecimalRegex.replace(raw) { m -> "${m.groupValues[1]}.${m.groupValues[2]}" }

    fun parse(json: String, fallbackCurrency: String = ""): ReceiptData? {
        return try {
            val start = json.indexOf('{')
            val end = json.lastIndexOf('}')
            if (start == -1 || end == -1 || end <= start) return null

            // Normalise comma-decimals BEFORE parsing so JSON stays valid
            val cleanJson = normalizeDecimalSeparator(json.substring(start, end + 1))
            val response = jsonConfig.decodeFromString<AiReceiptResponse>(cleanJson)

            val items = response.items.mapNotNull {
                if (it.name.isBlank() || it.price <= 0.0) null
                else ReceiptItem(
                    name = it.name,
                    price = it.price,
                    // prefer numeric qty field, fall back to legacy string "quantity"
                    quantity = if (it.qty != 1.0) {
                        val rounded = (it.qty * 100).toLong() / 100.0
                        if (rounded == rounded.toLong().toDouble()) rounded.toLong().toString()
                        else rounded.toString()
                    } else it.quantity.trim(),
                    taxRate = it.tax.trim()
                )
            }

            if (items.isEmpty() && response.total == null) return null

            ReceiptData(
                totalAmount = response.total,
                currency = response.currency?.takeIf { it.isNotBlank() } ?: fallbackCurrency,
                items = items
            )
        } catch (e: Exception) {
            // Use a simple println for commonMain logging or rely on caller's logging
            null
        }
    }
}
