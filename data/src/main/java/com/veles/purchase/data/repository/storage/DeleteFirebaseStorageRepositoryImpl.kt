package com.veles.purchase.data.repository.storage

import com.google.firebase.storage.FirebaseStorage
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.DeleteFirebaseStorageRepository
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class DeleteFirebaseStorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val logger: Logger
) : DeleteFirebaseStorageRepository {

    override suspend fun deletePhoto(purchaseModel: PurchasePhotoModel): Boolean {
        return try {
            storage.reference.child(purchaseModel.purchaseId).child(purchaseModel.purchasePhotoId)
                .delete().await()
            true
        } catch (e: Exception) {
            logger.e(
                DeleteFirebaseStorageRepository::class.java.name,
                e.message,
                e
            )
            false
        }
    }
}
