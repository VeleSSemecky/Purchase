package com.veles.purchase.platform.scanner

import android.graphics.BitmapFactory
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AndroidTextRecognizer : TextRecognizer {

    // Using default (Latin) recognizer — works for Polish, English, numbers, prices.
    // If Ukrainian/Cyrillic support is needed, swap with:
    // TextRecognition.getClient(CyrillicTextRecognizerOptions.Builder().build())
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override suspend fun recognizeText(imageBytes: ByteArray): List<String> =
        suspendCoroutine { continuation ->
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            if (bitmap == null) {
                android.util.Log.w("Scanner", "decodeByteArray returned null — invalid image bytes")
                continuation.resume(emptyList())
                return@suspendCoroutine
            }

            val image = InputImage.fromBitmap(bitmap, 0)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val lines = visionText.textBlocks.flatMap { block ->
                        block.lines.map { it.text }
                    }
                    android.util.Log.d("Scanner", "=== OCR lines (${lines.size}) ===")
                    lines.forEachIndexed { i, line -> android.util.Log.d("Scanner", "  [$i] '$line'") }
                    continuation.resume(lines)
                }
                .addOnFailureListener { e ->
                    android.util.Log.e("Scanner", "ML Kit recognition failed: ${e.message}", e)
                    continuation.resumeWithException(e)
                }
        }
}
