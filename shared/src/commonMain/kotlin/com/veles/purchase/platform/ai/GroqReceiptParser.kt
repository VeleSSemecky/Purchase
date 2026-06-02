package com.veles.purchase.platform.ai

import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.domain.model.scanner.ReceiptData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class GroqReceiptParser(
    private val httpClient: HttpClient
) : ReceiptAiParser {

    override val engineType: ReceiptAiParser.EngineType = ReceiptAiParser.EngineType.GROQ_CLOUD

    override suspend fun isAvailable(): Boolean {
        return EnvironmentConfig.GROQ_API_KEY.isNotEmpty()
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun parse(imageBytes: ByteArray): ReceiptData? {
        if (!isAvailable()) return null

        return try {
            val base64Image = Base64.encode(imageBytes)
            val mimeType = detectMimeType(imageBytes)
            val prompt = ReceiptPromptBuilder.buildPrompt()

            val response: HttpResponse = httpClient.post("https://api.groq.com/openai/v1/chat/completions") {
                header("Authorization", "Bearer ${EnvironmentConfig.GROQ_API_KEY}")
                contentType(ContentType.Application.Json)
                setBody(
                    GroqRequest(
                        model = "meta-llama/llama-4-scout-17b-16e-instruct",
                        messages = listOf(
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
                        temperature = 0.1
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

            val json = extractJson(content)
            ReceiptResponseParser.parse(json ?: content)
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

    private fun extractJson(text: String): String? {
        val start = text.indexOf('{')
        val end = text.lastIndexOf('}')
        return if (start != -1 && end != -1 && end > start) {
            text.substring(start, end + 1)
        } else null
    }

    @Serializable
    private data class GroqRequest(
        val model: String,
        val messages: List<GroqMessage>,
        val temperature: Double
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
