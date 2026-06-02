package com.veles.purchase.platform.scanner

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIImage
import platform.Vision.VNImageRequestHandler
import platform.Vision.VNRecognizeTextRequest
import platform.Vision.VNRecognizeTextRequestRevision3
import platform.Vision.VNRecognizedText
import platform.Vision.VNRecognizedTextObservation
import platform.Vision.VNRequestTextRecognitionLevelAccurate
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class IosTextRecognizer : TextRecognizer {

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun recognizeText(imageBytes: ByteArray): List<String> =
        suspendCancellableCoroutine { continuation ->
            val nsData = imageBytes.usePinned { pinned ->
                NSData.create(bytes = pinned.addressOf(0), length = imageBytes.size.toULong())
            }

            val uiImage = UIImage.imageWithData(nsData)
            if (uiImage?.CGImage == null) {
                continuation.resume(emptyList())
                return@suspendCancellableCoroutine
            }

            val request = VNRecognizeTextRequest { request, error ->
                if (error != null) {
                    continuation.resumeWithException(Exception(error.localizedDescription))
                    return@VNRecognizeTextRequest
                }
                val lines = request?.results
                    ?.filterIsInstance<VNRecognizedTextObservation>()
                    ?.mapNotNull { observation ->
                        (observation.topCandidates(1u).firstOrNull() as? VNRecognizedText)?.string
                    }
                    ?: emptyList()
                continuation.resume(lines)
            }

            request.recognitionLevel = VNRequestTextRecognitionLevelAccurate
            request.usesLanguageCorrection = true
            request.revision = VNRecognizeTextRequestRevision3

            val handler = VNImageRequestHandler(cGImage = uiImage.CGImage!!, options = emptyMap<Any?, Any?>())
            try {
                handler.performRequests(listOf(request), null)
            } catch (e: Exception) {
                if (continuation.isActive) continuation.resumeWithException(e)
            }
        }
}
