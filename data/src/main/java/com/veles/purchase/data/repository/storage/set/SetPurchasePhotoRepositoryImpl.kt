package com.veles.purchase.data.repository.storage.set

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.veles.purchase.data.core.extensions.toUnit
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.SetPurchasePhotoRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class SetPurchasePhotoRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : SetPurchasePhotoRepository {

    override suspend fun setPurchasePhotos(list: List<PurchasePhotoModel>) =
        suspendCancellableCoroutineWithTimeout {
            list.map { purchasePhotoModel ->
                storage.reference
                    .child(purchasePhotoModel.purchaseId)
                    .child(purchasePhotoModel.purchasePhotoId)
                    .putFile(Uri.parse(purchasePhotoModel.purchasePhotoUri))
                    .await()
            }.toUnit()
        }
}
