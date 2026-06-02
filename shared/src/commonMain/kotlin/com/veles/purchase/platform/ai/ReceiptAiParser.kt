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
        LOCAL_SLM
    }
}

// ── Shared prompt engineering ─────────────────────────────────────────────────

internal object ReceiptPromptBuilder {

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
     * Prompt for text-only models (Gemma 1B). Pre-processes OCR lines into a
     * structured format before sending so the small model doesn't need to
     * interpret raw tabular receipt noise.
     */
    fun buildStructuredPrompt(lines: List<String>): String {
        val structuredText = buildStructuredText(lines)
        return """
            Receipt text (one item per line in "name: price" format, last line is TOTAL):
            $structuredText
            
            Extract into JSON: {"total": number, "currency": "EUR", "items": [{"name": "...", "price": number}]}
            Use REAL values from the text. Return ONLY the JSON object, no markdown.
        """.trimIndent()
    }

    private val skipLinePattern = Regex(
        "(subtotal|sub-total|importe|iva|tax|tip|change|propina|vat|mwst|tva|" +
        "discount|descuento|rabatt|paid|pagado|bezahlt|cash|efectivo|" +
        "card|tarjeta|karte|receipt|recibo|bon|ticket|thank|gracias|danke|" +
        "tel|www|http|unselect|select all|" +
        // receipt table column headers
        "^producto\$|^precio\$|^cant\\.?\$|^qty\$|^article\$|^menge\$|" +
        // phone UI chrome — gallery picker buttons, status bar fragments
        "^done\$|^cancel\$|^share\$|" +
        "\\.{3,}|---|\\s*:?\\s*\$)",
        RegexOption.IGNORE_CASE
    )
    // Line-level noise: too few real letters → status bar icons, battery %, signal text
    private val tooFewLettersRegex = Regex("^[^a-zA-ZÀ-öø-ÿ]{0,2}\$")
    // Status-bar time pattern e.g. "19:26"
    private val timePatternRegex = Regex("""^\d{1,2}:\d{2}\s*$""")
    private val currencyPriceRegex = Regex(
        """([€${'$'}£¥₴₽])\s*([0-9]+[.,][0-9]{1,2})|([0-9]+[.,][0-9]{1,2})\s*([€${'$'}£¥₴₽])|([0-9]+[.,][0-9]{1,2})\s*(EUR|PLN|USD|GBP|UAH|CZK|HUF|CHF)\b""",
        RegexOption.IGNORE_CASE
    )
    private val totalKeywords = setOf("total", "suma", "suma total", "importe total", "amount due", "gesamtbetrag", "montant")

    private fun buildStructuredText(lines: List<String>): String {
        val sb = StringBuilder()
        var currency = "EUR"
        // Saved candidate name from the previous line that had no price (split-column rows)
        var prevNameLine = ""

        for (line in lines) {
            val trimmed = line.trim()
            if (trimmed.isBlank()) continue

            // Drop lines that are purely symbols / have < 3 real letters (status-bar noise)
            val letterCount = trimmed.count { it.isLetter() }
            if (letterCount < 3) continue
            if (timePatternRegex.matches(trimmed)) continue
            if (skipLinePattern.containsMatchIn(trimmed)) continue

            val allPrices = currencyPriceRegex.findAll(trimmed).toList()

            if (allPrices.isEmpty()) {
                // No price on this line → save as candidate name for the next (price-only) line
                prevNameLine = trimmed
                continue
            }

            // Update detected currency from any price match on this line
            for (m in allPrices) {
                val sym = m.groupValues[1].ifEmpty { m.groupValues[4].ifEmpty { null } }
                if (sym != null) currency = when (sym) {
                    "€" -> "EUR"; "$" -> "USD"; "£" -> "GBP"; "¥" -> "JPY"
                    "₴" -> "UAH"; "₽" -> "RUB"; else -> currency
                }
                val explicit = m.groupValues[6].ifEmpty { null }
                if (explicit != null) currency = explicit.uppercase()
            }

            // For multi-column receipts (name | unit-price | qty | total):
            // • use the LAST price on the line → it's the row total (rightmost column)
            // • name = everything BEFORE the first price on the line
            val lastPrice = allPrices.last()
            val priceVal = (lastPrice.groupValues[2].ifEmpty { null }
                ?: lastPrice.groupValues[3].ifEmpty { null }
                ?: lastPrice.groupValues[5].ifEmpty { null }) ?: continue

            // Text before the first price = item name (handles inline names)
            val inlineNameRaw = trimmed.substring(0, allPrices.first().range.first).trim()
            val inlineName = inlineNameRaw.takeIf { it.count { c -> c.isLetter() } >= 3 }

            val name = (inlineName ?: prevNameLine.takeIf { it.isNotBlank() })?.trim()
            prevNameLine = "" // consume it regardless

            if (name.isNullOrBlank() || skipLinePattern.containsMatchIn(name)) continue

            val isTotal = totalKeywords.any { name.lowercase().contains(it) }
            val priceFormatted = priceVal.replace(',', '.')
            if (isTotal) {
                sb.append("TOTAL($currency): $priceFormatted\n")
            } else {
                sb.append("$name: $priceFormatted\n")
            }
        }

        return sb.toString().trim()
    }
}

internal object ReceiptResponseParser {
    private val totalRegex = Regex(""""total"\s*:\s*"?([0-9]+(?:[.,][0-9]+)?)"?""")
    private val currencyRegex = Regex(""""currency"\s*:\s*"([A-Z]{2,5})"""")
    // Handles both numeric ("price":9.50) and string ("price":"9.50") and comma decimals
    private val itemRegex = Regex(""""name"\s*:\s*"([^"]+)"\s*,\s*"price"\s*:\s*"?([0-9]+(?:[.,][0-9]+)?)"?""")

    fun parse(json: String, fallbackCurrency: String = ""): com.veles.purchase.domain.model.scanner.ReceiptData? {
        return try {
            val total = totalRegex.find(json)?.groupValues?.get(1)?.replace(',', '.')?.toDoubleOrNull()
            val currency = currencyRegex.find(json)?.groupValues?.get(1) ?: fallbackCurrency
            val items = itemRegex.findAll(json).map { m ->
                com.veles.purchase.domain.model.scanner.ReceiptItem(
                    name = m.groupValues[1],
                    price = m.groupValues[2].replace(',', '.').toDoubleOrNull() ?: 0.0
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
