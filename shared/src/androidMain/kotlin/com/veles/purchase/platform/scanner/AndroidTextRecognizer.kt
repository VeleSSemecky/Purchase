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
                continuation.resume(emptyList())
                return@suspendCoroutine
            }

            val image = InputImage.fromBitmap(bitmap, 0)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val lines = visionText.textBlocks.flatMap { block ->
                        block.lines.map { it.text }
                    }
                    continuation.resume(lines)
                }
                .addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }
        }
}
