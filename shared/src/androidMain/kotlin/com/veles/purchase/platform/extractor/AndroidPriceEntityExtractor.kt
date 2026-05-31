package com.veles.purchase.platform.extractor

import com.google.mlkit.nl.entityextraction.EntityAnnotation
import com.google.mlkit.nl.entityextraction.EntityExtraction
import com.google.mlkit.nl.entityextraction.EntityExtractionParams
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions
import com.google.mlkit.nl.entityextraction.MoneyEntity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidPriceEntityExtractor : PriceEntityExtractor {

    private val extractor = EntityExtraction.getClient(
        EntityExtractorOptions.Builder(EntityExtractorOptions.POLISH).build()
    )

    override suspend fun extractMoneyEntities(text: String): List<MoneyAnnotation> {
        // Download the Polish on-device model on first use (~22MB, cached afterwards)
        suspendCancellableCoroutine<Unit> { continuation ->
            extractor.downloadModelIfNeeded()
                .addOnSuccessListener { continuation.resume(Unit) }
                .addOnFailureListener { e: Exception ->
                    if (continuation.isActive) continuation.resumeWithException(e)
                }
        }

        val annotations: List<EntityAnnotation> = suspendCancellableCoroutine { continuation ->
            val params = EntityExtractionParams.Builder(text).build()
            extractor.annotate(params)
                .addOnSuccessListener { result: List<EntityAnnotation> ->
                    continuation.resume(result)
                }
                .addOnFailureListener { e: Exception ->
                    if (continuation.isActive) continuation.resumeWithException(e)
                }
        }

        return annotations.flatMap { annotation ->
            annotation.entities
                .filterIsInstance<MoneyEntity>()
                .map { money ->
                    val amount = money.integerPart.toDouble() + (money.fractionalPart / 100.0)
                    MoneyAnnotation(
                        text = annotation.annotatedText,
                        start = annotation.start,
                        end = annotation.end,
                        amount = amount,
                        currency = money.unnormalizedCurrency ?: ""
                    )
                }
        }
    }
}
