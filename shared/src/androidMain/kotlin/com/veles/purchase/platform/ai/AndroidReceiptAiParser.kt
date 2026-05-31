package com.veles.purchase.platform.ai

import android.content.Context
import android.util.Log
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.genai.llminference.LlmInferenceSession
import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.domain.usecase.scanner.ParseReceiptUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

private const val TAG = "ReceiptAiParser"

/**
 * Android implementation of [ReceiptAiParser].
 *
 * Uses MediaPipe LLM Inference (Gemma 3 1B, INT4, ~689 MB) when the model
 * has been downloaded. Falls back to regex [ParseReceiptUseCase] otherwise.
 *
 * Download flow: see [GemmaSetupViewModel].
 * Note: Temperature is set per-session via [LlmInferenceSession] (API v0.10.22+).
 */
class AndroidReceiptAiParser(
    private val context: Context,
    private val modelRepo: AndroidGemmaModelRepository,
    @Suppress("UNUSED_PARAMETER") fallback: ParseReceiptUseCase
) : ReceiptAiParser {

    private var llm: LlmInference? = null

    override suspend fun isAvailable(): Boolean {
        if (!modelRepo.isModelDownloaded()) return false
        // Gemma 3 1B needs ~1.5 GB RAM — skip on low-memory devices (< 3 GB)
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        val memInfo = android.app.ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)
        val totalRamMb = memInfo.totalMem / (1024 * 1024)
        if (totalRamMb < 3_000) {
            Log.w(TAG, "Skipping AI parser — device has only ${totalRamMb}MB RAM (need 3GB+)")
            return false
        }
        return true
    }

    override suspend fun parse(lines: List<String>): ReceiptData? {
        if (!isAvailable()) return null
        return try {
            val model = getOrCreateLlm() ?: return null
            val prompt = ReceiptPromptBuilder.buildPrompt(lines)
            Log.d(TAG, "Sending to Gemma 3 (${lines.size} lines)")

            val response: String? = withTimeoutOrNull(30_000L) {
                withContext(Dispatchers.Default) {
                    val sessionOpts = LlmInferenceSession.LlmInferenceSessionOptions.builder()
                        .setTemperature(GemmaModelConfig.TEMPERATURE)
                        .build()
                    val session = LlmInferenceSession.createFromOptions(model, sessionOpts)
                    try {
                        session.addQueryChunk(prompt)
                        suspendCancellableCoroutine { cont ->
                            val future = session.generateResponseAsync { partial, done ->
                                if (done) cont.resume(partial ?: "")
                            }
                            cont.invokeOnCancellation { future.cancel(true) }
                        }
                    } finally {
                        session.close()
                    }
                }
            }

            if (response == null) {
                Log.w(TAG, "Gemma 3 timed out"); return null
            }
            Log.d(TAG, "Gemma 3 response: $response")

            ReceiptResponseParser.parse(response).also { result ->
                if (result == null) Log.w(TAG, "Response not valid JSON — regex fallback")
                else Log.d(TAG, "AI: total=${result.totalAmount} items=${result.items.size}")
            }
        } catch (e: Exception) {
            Log.w(TAG, "Gemma 3 failed (${e.message}) — regex fallback"); null
        }
    }

    private fun getOrCreateLlm(): LlmInference? {
        llm?.let { return it }
        return try {
            val opts = LlmInference.LlmInferenceOptions.builder()
                .setModelPath(modelRepo.manager.modelFile.absolutePath)
                .setMaxTokens(GemmaModelConfig.MAX_TOKENS)
                .build()
            LlmInference.createFromOptions(context, opts).also { llm = it }
        } catch (e: Exception) {
            Log.e(TAG, "LlmInference init failed: ${e.message}"); null
        }
    }

    fun close() { llm?.close(); llm = null }
}
