package com.veles.purchase.platform.ai

import android.content.Context
import android.util.Log
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.domain.model.scanner.ReceiptItem
import com.veles.purchase.platform.scanner.StructuredRowParser
import com.veles.purchase.platform.scanner.TextRecognizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.resume

private const val TAG = "OcrTextParser"
/** Timeout for the GENERATION phase only — model loading is a separate step. */
private const val GENERATION_TIMEOUT_MS = 580_000L
/** Timeout for the initial model LOAD (happens once per app session). */
private const val LOAD_TIMEOUT_MS = 120_000L
private const val TEXT_MODEL_MAX_TOKENS = 2048
// How many trailing chars to print per partial-stream log entry
private const val STREAM_LOG_TAIL = 120

/**
 * Fallback engine: Google ML Kit OCR with coordinate row rebuilding + text-only Gemma.
 *
 * The [LlmInference] instance is cached after the first [parse] call so that
 * subsequent invocations skip the expensive model-loading step (~30-60 s for a 3.5 GB model).
 *
 * Registered as a Koin `single`, so the cached instance lives for the app session.
 */
class AndroidOcrTextParser(
    private val context: Context,
    private val textRecognizer: TextRecognizer,
    private val modelFile: File
) : ReceiptAiParser {

    override val engineType: ReceiptAiParser.EngineType = ReceiptAiParser.EngineType.OCR_TEXT_MODEL

    // ── Model cache ───────────────────────────────────────────────────────────
    private val modelMutex = Mutex()
    @Volatile private var cachedModel: LlmInference? = null

    /**
     * Returns a cached [LlmInference] instance, creating it if necessary.
     * Model creation is serialised via [modelMutex] and wrapped in its own timeout.
     */
    private suspend fun getOrCreateModel(): LlmInference? {
        cachedModel?.let { return it }
        return modelMutex.withLock {
            // Double-checked after acquiring lock
            cachedModel?.let { return@withLock it }

            Log.d(TAG, "Step 3.1: loading LlmInference model (first call), path=${modelFile.name}")
            val loaded = withTimeoutOrNull(LOAD_TIMEOUT_MS) {
                withContext(Dispatchers.IO) {
                    val opts = LlmInference.LlmInferenceOptions.builder()
                        .setModelPath(modelFile.absolutePath)
                        .setMaxTokens(TEXT_MODEL_MAX_TOKENS)
                        .build()
                    LlmInference.createFromOptions(context, opts)
                }
            }
            if (loaded == null) {
                Log.e(TAG, "Step 3.1 FAILED: model load timed out after ${LOAD_TIMEOUT_MS / 1000}s")
            } else {
                Log.d(TAG, "Step 3.1 complete: model loaded and cached")
                cachedModel = loaded
            }
            loaded
        }
    }

    /** Call to free model memory when no longer needed (e.g. on low-memory). */
    fun releaseModel() {
        val m = cachedModel
        cachedModel = null
        try { m?.close() } catch (_: Exception) {}
        Log.d(TAG, "Cached model released")
    }

    // ── ReceiptAiParser ───────────────────────────────────────────────────────

    override suspend fun isAvailable(): Boolean {
        val exists = modelFile.exists()
        val sizeBytes = if (exists) modelFile.length() else 0L
        val available = exists && sizeBytes > 500_000_000
        Log.d(
            TAG,
            "Availability check: available=$available, exists=$exists, sizeMb=${sizeBytes / 1024 / 1024}, path=${modelFile.absolutePath}"
        )
        return available
    }

    override suspend fun parse(imageBytes: ByteArray): ReceiptData? {
        Log.d(TAG, "OCR_TEXT_LLM pipeline started: imageBytes=${imageBytes.size}")
        if (!isAvailable()) {
            Log.w(TAG, "OCR_TEXT_LLM stopped: local text model is not available")
            return null
        }

        return try {
            // ── Step 1: ML Kit OCR ────────────────────────────────────────────
            Log.d(TAG, "Step 1: run ML Kit OCR + coordinate row rebuild")
            val lines = textRecognizer.recognizeText(imageBytes)
            if (lines.isEmpty()) {
                Log.w(TAG, "Step 1 complete but no OCR rows were returned")
                return null
            }
            Log.d(TAG, "Step 1 complete: alignedRows=${lines.size}\n${lines.joinToString("\n")}")

            // ── Step 2a: direct structured parse (DISABLED for LLM testing) ──
            /*
            Log.d(TAG, "Step 2a: attempt direct structured parse from tab-separated rows")
            val directResult = StructuredRowParser.parse(lines)
            if (directResult != null) {
                val receiptData = ReceiptData(
                    totalAmount = directResult.total,
                    currency = directResult.currency,
                    items = directResult.items.map { ReceiptItem(name = it.name, price = it.price) }
                )
                Log.d(TAG, "Step 2a SUCCESS (no LLM needed): items=${directResult.items.size}, total=${directResult.total}, currency='${directResult.currency}'")
                return receiptData
            }
            */
            Log.d(TAG, "Step 2a skipped: testing LLM as corrector")
            Log.w(TAG, "Step 2a: direct parse found no items — falling back to LLM")

            // ── Step 2b: build LLM prompt ─────────────────────────────────────
            Log.d(TAG, "Step 2b: build text-only Gemma prompt")
            val promptTruncated = ReceiptPromptBuilder.isOcrTextTruncated(lines)
            val prompt = ReceiptPromptBuilder.buildOcrTextPrompt(lines)
            if (prompt.isBlank()) {
                Log.w(TAG, "Step 2b failed: prompt is blank")
                return null
            }
            Log.d(TAG, "Step 2b complete: rows=${lines.size}, promptChars=${prompt.length}, truncated=$promptTruncated\n$prompt")

            // ── Step 3: LLM inference (model load is cached) ──────────────────
            Log.d(TAG, "Step 3: ensure model loaded")
            val model = getOrCreateModel() ?: run {
                Log.e(TAG, "Step 3 FAILED: model unavailable")
                return null
            }

            Log.d(TAG, "Step 3: run Gemma inference (generationTimeout=${GENERATION_TIMEOUT_MS / 1000}s)")
            val response = runGeneration(model, prompt) ?: run {
                Log.w(TAG, "Step 3 FAILED: Gemma returned null or timed out after ${GENERATION_TIMEOUT_MS / 1000}s")
                // Model may be in a bad state after timeout; evict cache so next call reloads.
                releaseModel()
                return null
            }
            if (response.isBlank()) {
                Log.w(TAG, "Step 3 FAILED: Gemma returned empty response")
                releaseModel()
                return null
            }
            Log.d(TAG, "Step 3 complete: responseLength=${response.length}\n--- RESPONSE BEGIN ---\n$response\n--- RESPONSE END ---")

            // ── Step 4: extract JSON ──────────────────────────────────────────
            Log.d(TAG, "Step 4: extract JSON from Gemma response")
            val json = extractCompleteJson(response) ?: run {
                Log.w(TAG, "Step 4: no balanced JSON found, trying fallback")
                extractJsonFallback(response)
            }
            Log.d(TAG, "Step 4 complete: jsonLength=${json?.length ?: -1}, json=${json ?: "<none>"}")

            // ── Step 5: parse JSON → ReceiptData ──────────────────────────────
            Log.d(TAG, "Step 5: parse JSON into ReceiptData")
            val parsed = ReceiptResponseParser.parse(json ?: response)
            Log.d(TAG, "Step 5 complete: parsed=$parsed")
            parsed

        } catch (e: Exception) {
            Log.e(TAG, "OCR_TEXT_LLM pipeline failed: ${e.message}", e)
            null
        }
    }

    // ── Generation ────────────────────────────────────────────────────────────

    /**
     * Runs [LlmInference.generateResponseAsync] with a [GENERATION_TIMEOUT_MS] timeout.
     *
     * The model is NOT closed on timeout — the cached instance remains valid.
     * If the timeout fires mid-generation the next call will resume normally.
     */
    private suspend fun runGeneration(model: LlmInference, prompt: String): String? =
        withTimeoutOrNull(GENERATION_TIMEOUT_MS) {
            withContext(Dispatchers.IO) {
                suspendCancellableCoroutine { cont ->
                    val response = StringBuilder()
                    val resumed = AtomicBoolean(false)

                    Log.d(TAG, "Step 3.2: generateResponseAsync started")
                    model.generateResponseAsync(prompt) { partial, done ->
                        if (resumed.get()) return@generateResponseAsync

                        response.append(partial)
                        val total = response.length
                        val completedJson = extractCompleteJson(response.toString())

                        val tail = response.takeLast(STREAM_LOG_TAIL)
                        Log.d(
                            TAG,
                            "Step 3.3: +${partial.length} chars, total=$total, done=$done, hasJson=${completedJson != null}, tail='$tail'"
                        )

                        if (completedJson != null && resumed.compareAndSet(false, true)) {
                            Log.d(TAG, "Step 3.4: complete JSON found early at $total chars, jsonLength=${completedJson.length}")
                            cont.resume(completedJson)
                            return@generateResponseAsync
                        }

                        if (done && resumed.compareAndSet(false, true)) {
                            Log.d(TAG, "Step 3.4: generation done, finalLength=$total")
                            cont.resume(response.toString())
                        }
                    }
                }
            }
        }

    // ── JSON helpers ────────────────────────��─────────────────────────────────

    /** Finds the first balanced `{...}` object in [text] — safe for streaming. */
    private fun extractCompleteJson(text: String): String? {
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
                !inString && ch == '}' -> { depth--; if (depth == 0) return text.substring(start, i + 1) }
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
}
