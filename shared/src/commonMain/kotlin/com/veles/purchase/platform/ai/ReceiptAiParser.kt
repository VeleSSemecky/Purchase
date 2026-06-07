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

            Example:
            Input:
            CocaCola 0.2 | 3.00 | 1 | 3.00
            New York Sour | 8.00 | 2 | 16.00
            Total | 19.00
            Output: {"total":19.00,"currency":"EUR","items":[{"name":"CocaCola 0.2","price":3.00},{"name":"New York Sour","price":16.00}]}

            RECEIPT OCR DATA:
            $alignedText

            Return ONLY raw JSON:
            {"total":number|null,"currency":"ISO_CODE","items":[{"name":"string","price":number}]}
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
        val price: Double
    )

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    fun parse(json: String, fallbackCurrency: String = ""): ReceiptData? {
        return try {
            // Find the first '{' and the last '}' to extract a clean JSON object
            val start = json.indexOf('{')
            val end = json.lastIndexOf('}')
            if (start == -1 || end == -1 || end <= start) return null
            
            val cleanJson = json.substring(start, end + 1)
            val response = jsonConfig.decodeFromString<AiReceiptResponse>(cleanJson)

            val items = response.items.mapNotNull {
                if (it.name.isBlank() || it.price <= 0.0) null
                else ReceiptItem(name = it.name, price = it.price)
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
