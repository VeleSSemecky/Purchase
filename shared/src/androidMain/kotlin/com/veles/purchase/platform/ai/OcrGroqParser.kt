package com.veles.purchase.platform.ai

import android.util.Log
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.platform.scanner.TextRecognizer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

private const val TAG = "OcrGroqParser"

/**
 * Two-pass receipt parser:
 *  1. ML Kit OCR (local, offline) — reads image pixels → raw text rows
 *  2. Groq llama-3.3-70b-versatile (cloud, text-only API) — parses text → JSON
 *
 * Why this is better than vision-only Groq (llama-4-scout):
 *  - ML Kit OCR reads text accurately even on dense Polish/Spanish receipts
 *  - llama-3.3-70b is 4× larger than scout → much better at parsing structure
 *  - Text tokens are much cheaper than image tokens → faster response
 *  - No base64 image in the request
 */
class OcrGroqParser(
    private val httpClient: HttpClient,
    private val textRecognizer: TextRecognizer
) : ReceiptAiParser {

    override val engineType: ReceiptAiParser.EngineType = ReceiptAiParser.EngineType.OCR_GROQ

    override suspend fun isAvailable(): Boolean =
        EnvironmentConfig.GROQ_API_KEY.isNotEmpty()

    override suspend fun parse(imageBytes: ByteArray): ReceiptData? {
        if (!isAvailable()) return null

        return try {
            // ── Step 1: ML Kit OCR ────────────────────────────────────────────
            Log.d(TAG, "Step 1: ML Kit OCR")
            val lines = textRecognizer.recognizeText(imageBytes)
            if (lines.isEmpty()) {
                Log.w(TAG, "Step 1: no text recognized")
                return null
            }
            val ocrText = lines.joinToString("\n")
            Log.d(TAG, "Step 1 complete: ${lines.size} lines\n$ocrText")

            // ── Step 2: send text to Groq ─────────────────────────────────────
            Log.d(TAG, "Step 2: send OCR text to Groq llama-3.3-70b-versatile")
            val prompt = buildTextPrompt(ocrText)

            val response: HttpResponse = httpClient.post("https://api.groq.com/openai/v1/chat/completions") {
                header("Authorization", "Bearer ${EnvironmentConfig.GROQ_API_KEY}")
                contentType(ContentType.Application.Json)
                setBody(
                    GroqTextRequest(
                        model = "llama-3.3-70b-versatile",
                        messages = listOf(
                            GroqTextMessage(
                                role = "system",
                                content = "You are a precise receipt data extractor. Return ONLY valid JSON. No markdown, no explanations."
                            ),
                            GroqTextMessage(
                                role = "user",
                                content = prompt
                            )
                        ),
                        temperature = 0.0,
                        max_tokens = 4096,
                        response_format = GroqResponseFormat(type = "json_object")
                    )
                )
            }

            if (response.status != HttpStatusCode.OK) {
                Log.e(TAG, "Groq API error: ${response.status} - ${response.bodyAsText()}")
                return null
            }

            val groqResponse: GroqTextResponse = response.body()
            val content = groqResponse.choices.firstOrNull()?.message?.content ?: return null

            Log.d(TAG, "=== OCR+GROQ RAW RESPONSE ===\n$content\n=== END ===")

            val json = extractBalancedJson(content) ?: content
            val parsed = ReceiptResponseParser.parse(json)
            Log.d(TAG, "=== OCR+GROQ PARSED === items=${parsed?.items?.size} total=${parsed?.totalAmount} currency=${parsed?.currency}")
            parsed

        } catch (e: Exception) {
            Log.e(TAG, "OcrGroqParser failed: ${e.message}", e)
            null
        }
    }

    private fun buildTextPrompt(ocrText: String): String = """
        You are a receipt parser. Below is the raw OCR text extracted from a receipt image.
        
        Return ONLY this JSON, nothing else:
        {"total":number,"currency":"ISO_CODE","items":[{"name":"string","price":number,"qty":number,"tax":"string"}]}

        Field definitions:
        - "price" = the LINE TOTAL for that item (the rightmost/last price on the product line). If qty=2 and unit price=8.00 and line total=16.00, then price=16.00 NOT 8.00.
        - "qty" = quantity as a number. Use decimals for weight items (0.232 for 0,232 kg). Default 1.
        - "tax" = per-line VAT code or letter if printed right after the price (e.g. "A", "B", "C", "23%"). Otherwise "".
        - "total" = the final amount paid. Look for "DO ZAPŁATY", "TOTAL", "Cantidad a pagar", or the last/largest total line.
        - "currency" = ISO code from the symbol (€→EUR, ${'$'}→USD, £→GBP, zł/PLN→PLN, ₴→UAH).

        Rules:
        - Always use DOT as decimal separator in JSON (receipt may use comma: 14,99 → 14.99).
        - Tax codes are sometimes written immediately after the price with no space: "15,79A" → price=15.79, tax="A".
        - Discounts (OPUST, descuento, rabat) are items with negative price — include them.
        - Returnable packaging (butelka, kaucja) that adds to the total — include as item.
        - Extract EVERY product line. Do not skip or merge items.
        - A valid item line has a product NAME and a PRICE. Skip rows that are ONLY numbers.
        "- SKIP: PTU/IVA/SUMA PTU/SPRZEDAŻ OPODATKOWANA rows, OPUSTY ŁĄCZNIE (discount totals), subtotals (SUMA PLN, Parcial), payment rows (KARTA, EFECTIVO, CASH), store header, address, dates, loyalty card info, footer."

        RECEIPT OCR TEXT:
        $ocrText
    """.trimIndent()

    private fun extractBalancedJson(text: String): String? {
        val start = text.indexOf('{')
        if (start == -1) return null
        var depth = 0
        var inString = false
        var escape = false
        for (i in start until text.length) {
            val ch = text[i]
            when {
                escape -> escape = false
                inString && ch == '\\' -> escape = true
                ch == '"' -> inString = !inString
                !inString && ch == '{' -> depth++
                !inString && ch == '}' -> {
                    depth--
                    if (depth == 0) return text.substring(start, i + 1)
                }
            }
        }
        return null
    }

    // ── Serialization models ──────────────────────────────────────────────────

    @Serializable
    private data class GroqTextRequest(
        val model: String,
        val messages: List<GroqTextMessage>,
        val temperature: Double,
        val max_tokens: Int,
        val response_format: GroqResponseFormat
    )

    @Serializable
    private data class GroqResponseFormat(val type: String = "json_object")

    @Serializable
    private data class GroqTextMessage(val role: String, val content: String)

    @Serializable
    private data class GroqTextResponse(val choices: List<GroqTextChoice>)

    @Serializable
    private data class GroqTextChoice(val message: GroqTextMessage)
}

