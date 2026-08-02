package com.veles.purchase.platform.ai

import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.domain.model.scanner.ReceiptData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class GroqReceiptParser(
    private val httpClient: HttpClient
) : ReceiptAiParser {

    override val engineType: ReceiptAiParser.EngineType = ReceiptAiParser.EngineType.GROQ_CLOUD

    override suspend fun isAvailable(): Boolean {
        return EnvironmentConfig.GROQ_API_KEY.isNotEmpty()
    }

    // ── Groq-specific configuration ───────────────────────────────────────────
    // These values only affect GroqReceiptParser and have no impact on other
    // recognition engines (Gemini Nano, LocalMediaPipe, OCR-text, etc.).

    private val groqModel = "meta-llama/llama-4-scout-17b-16e-instruct"
    private val groqTemperature = 0.0   // 0 = maximally deterministic for structured output
    private val groqMaxTokens = 4096

    /** Groq-specific prompt. Changes here do NOT affect other parsers. */
    private fun buildGroqPrompt(): String = """
        You are a receipt parser. Look at the receipt image and extract ALL product line items.

        Return ONLY this JSON, nothing else:
        {"total":number,"currency":"ISO_CODE","items":[{"name":"string","price":number,"qty":number,"tax":"string"}]}

        Field definitions:
        - "price" = the LINE TOTAL amount (the rightmost/last price on that product line). If qty=2 and unit price=8.00 and line total=16.00, then price=16.00 NOT 8.00.
        - "qty" = quantity as a number. Use decimals for weight items (0.232 for 0,232 kg). Default 1.
        - "tax" = per-line VAT code or letter if printed right after the price (e.g. "A", "B", "C", "23%"). Otherwise "".
        - "total" = the final amount paid. Look for "DO ZAPŁATY", "TOTAL", "Cantidad a pagar" or the last/largest total line.
        - "currency" = ISO code from the symbol (€→EUR, ${'$'}→USD, £→GBP, zł/PLN→PLN, ₴→UAH).

        Rules:
        - Always use DOT as decimal separator in JSON (receipt may use comma: 14,99 → 14.99).
        - Discounts (OPUST, descuento) are items with negative price — include them.
        - Returnable packaging (butelka, kaucja) that adds to the total — include as item.
        - Extract EVERY product line from top to bottom. Do not skip or merge items.
        - A valid item line has a product NAME and a PRICE. Skip rows that are ONLY numbers.
        - SKIP these row types entirely: VAT/tax summary (PTU, IVA, SUMA PTU, SPRZEDAŻ OPODATKOWANA), subtotals (SUMA PLN, Parcial), payment rows (KARTA, EFECTIVO), store name/address, receipt numbers, dates, loyalty card info, footer text.
    """.trimIndent()

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun parse(imageBytes: ByteArray): ReceiptData? {
        if (!isAvailable()) return null

        return try {
            val base64Image = Base64.encode(imageBytes)
            val mimeType = detectMimeType(imageBytes)
            val prompt = buildGroqPrompt()

            val response: HttpResponse = httpClient.post("https://api.groq.com/openai/v1/chat/completions") {
                header("Authorization", "Bearer ${EnvironmentConfig.GROQ_API_KEY}")
                contentType(ContentType.Application.Json)
                setBody(
                    GroqRequest(
                        model = groqModel,
                        messages = listOf(
                            GroqMessage(
                                role = "system",
                                content = listOf(
                                    GroqContent(
                                        type = "text",
                                        text = "You are a precise receipt data extractor. Return ONLY valid JSON. No markdown, no explanations."
                                    )
                                )
                            ),
                            GroqMessage(
                                role = "user",
                                content = listOf(
                                    GroqContent(type = "text", text = prompt),
                                    GroqContent(
                                        type = "image_url",
                                        image_url = GroqImageUrl(url = "data:$mimeType;base64,$base64Image")
                                    )
                                )
                            )
                        ),
                        temperature = groqTemperature,
                        max_tokens = groqMaxTokens,
                        response_format = GroqResponseFormat(type = "json_object")
                    )
                )
            }

            if (response.status != HttpStatusCode.OK) {
                println("Groq API error: ${response.status} - ${response.bodyAsText()}")
                return null
            }

            val groqResponse: GroqResponse = response.body()
            val content = groqResponse.choices.firstOrNull()?.message?.content
            if (content == null) return null

            println("=== GROQ RAW RESPONSE ===\n$content\n=== END ===")

            val json = extractBalancedJson(content) ?: extractJsonFallback(content)
            val parsed = ReceiptResponseParser.parse(json ?: content)
            println("=== GROQ PARSED RESULT === items=${parsed?.items?.size} total=${parsed?.totalAmount} currency=${parsed?.currency}")
            parsed
        } catch (e: Exception) {
            println("Groq parsing failed: ${e.message}")
            null
        }
    }

    private fun detectMimeType(bytes: ByteArray): String = when {
        bytes.size >= 3 && bytes[0] == 0xFF.toByte() && bytes[1] == 0xD8.toByte() && bytes[2] == 0xFF.toByte() -> "image/jpeg"
        bytes.size >= 8 && bytes[0] == 0x89.toByte() && bytes[1] == 0x50.toByte() -> "image/png"
        bytes.size >= 4 && bytes[0] == 0x52.toByte() && bytes[1] == 0x49.toByte() && bytes[2] == 0x46.toByte() -> "image/webp"
        else -> "image/jpeg"
    }

    /** Finds the first balanced `{...}` block — safe against nested objects. */
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

    /** Fallback: substring between first `{` and last `}`. */
    private fun extractJsonFallback(text: String): String? {
        val start = text.indexOf('{')
        val end = text.lastIndexOf('}')
        return if (start != -1 && end > start) text.substring(start, end + 1) else null
    }

    @Serializable
    private data class GroqRequest(
        val model: String,
        val messages: List<GroqMessage>,
        val temperature: Double,
        val max_tokens: Int = 4096,
        val response_format: GroqResponseFormat = GroqResponseFormat()
    )

    @Serializable
    private data class GroqResponseFormat(
        val type: String = "json_object"
    )

    @Serializable
    private data class GroqMessage(
        val role: String,
        val content: List<GroqContent>
    )

    @Serializable
    private data class GroqContent(
        val type: String,
        val text: String? = null,
        val image_url: GroqImageUrl? = null
    )

    @Serializable
    private data class GroqImageUrl(
        val url: String
    )

    @Serializable
    private data class GroqResponse(
        val choices: List<GroqChoice>
    )

    @Serializable
    private data class GroqChoice(
        val message: GroqResponseMessage
    )

    @Serializable
    private data class GroqResponseMessage(
        val content: String
    )
}
