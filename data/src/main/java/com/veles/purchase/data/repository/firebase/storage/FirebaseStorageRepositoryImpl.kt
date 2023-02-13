package com.veles.purchase.data.repository.firebase.storage

import android.net.Uri
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.firebase.storage.FirebaseStorageRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.tasks.await

@Singleton
class FirebaseStorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : FirebaseStorageRepository {

    override suspend fun apiFirebaseStorage(list: List<PurchasePhotoModel>): Boolean =
        list.map { purchasePhotoModel ->
            suspendCancellableCoroutineWithTimeout(
                TimeUnit.SECONDS.toMillis(5.toLong())
            ) { continuation ->

                val uploadTask = storage.reference
                    .child(purchasePhotoModel.purchaseId)
                    .child(purchasePhotoModel.purchasePhotoId)
                    .putFile(Uri.parse(purchasePhotoModel.purchasePhotoUri))

                uploadTask.addOnSuccessListener {
                    continuation.resume(true)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                continuation.invokeOnCancellation {
                    uploadTask.cancel()
                }
            }
        }.any()

    override suspend fun apiFirebaseStorageDelete(
        purchaseCreateId: String
    ): Boolean = try {
        storage
            .reference
            .child(purchaseCreateId)
            .delete().await()
        true
    } catch (e: Exception) {
        Firebase.crashlytics.recordException(e)
        false
    }

    override fun getStoragePhoto(purchasePhotoModel: PurchasePhotoModel): Any =
        storage.reference
            .child(purchasePhotoModel.purchaseId)
            .child(purchasePhotoModel.purchasePhotoId)
}
