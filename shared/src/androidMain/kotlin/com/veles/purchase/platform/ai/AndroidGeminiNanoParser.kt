package com.veles.purchase.platform.ai

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import com.google.mlkit.genai.common.DownloadStatus
import com.google.mlkit.genai.common.FeatureStatus
import com.google.mlkit.genai.prompt.GenerateContentRequest
import com.google.mlkit.genai.prompt.Generation
import com.google.mlkit.genai.prompt.ImagePart
import com.google.mlkit.genai.prompt.TextPart
import com.veles.purchase.domain.model.scanner.ReceiptData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

private const val TAG = "GeminiNanoParser"

class AndroidGeminiNanoParser(
    private val context: Context
) : ReceiptAiParser {

    override val engineType: ReceiptAiParser.EngineType = ReceiptAiParser.EngineType.GEMINI_NANO

    private val generativeModel = Generation.getClient()

    private val _downloadProgress = MutableStateFlow(0f)
    val downloadProgress: Flow<Float> = _downloadProgress.asStateFlow()

    override suspend fun isAvailable(): Boolean {
        return try {
            generativeModel.checkStatus() == FeatureStatus.AVAILABLE
        } catch (e: Exception) {
            Log.w(TAG, "Failed to check Gemini Nano status: ${e.message}")
            false
        }
    }

    @FeatureStatus
    suspend fun getStatus(): Int = try {
        generativeModel.checkStatus()
    } catch (e: Exception) {
        FeatureStatus.UNAVAILABLE
    }

    suspend fun downloadModel() {
        try {
            generativeModel.download().first { it is DownloadStatus.DownloadCompleted || it is DownloadStatus.DownloadFailed }
        } catch (e: Exception) {
            Log.e(TAG, "Model download failed: ${e.message}")
        }
    }

    override suspend fun parse(imageBytes: ByteArray): ReceiptData? {
        if (!isAvailable()) return null

        return try {
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size) ?: return null

            val request = GenerateContentRequest.Builder(
                ImagePart(bitmap),
                TextPart(ReceiptPromptBuilder.buildPrompt())
            ).apply {
                temperature = 0.1f
                maxOutputTokens = 256
            }.build()

            val response = generativeModel.generateContent(request)
            val text = response.candidates.firstOrNull()?.text ?: return null

            val json = extractJson(text)
            ReceiptResponseParser.parse(json ?: text)
        } catch (e: Exception) {
            Log.e(TAG, "Gemini Nano parsing failed: ${e.message}", e)
            null
        }
    }

    private fun extractJson(text: String): String? {
        val start = text.indexOf('{')
        val end = text.lastIndexOf('}')
        return if (start != -1 && end != -1 && end > start) text.substring(start, end + 1) else null
    }
}

