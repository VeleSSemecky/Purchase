package com.veles.purchase.data.repository.storage.delete

import android.content.ContentResolver
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.veles.purchase.data.core.extensions.toUnit
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.DeletePurchasePhotoRepository
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class DeletePurchasePhotoRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val storage: FirebaseStorage,
    private val logger: Logger
) : DeletePurchasePhotoRepository {

    override suspend fun deletePhoto(purchasePhotoModel: PurchasePhotoModel) {
        when (purchasePhotoModel.status) {
            PhotoStatus.LOCAL -> deleteLocalPhoto(purchasePhotoModel)
            PhotoStatus.DOWNLOADED -> deleteStoragePhoto(purchasePhotoModel)
        }
    }

    override suspend fun deletePhotos(
        purchaseId: String,
        listImage: List<PurchasePhotoModel>
    ) {
        listImage.filter { it.status == PhotoStatus.LOCAL }.map { deleteLocalPhoto(it) }
        listImage.filter { it.status == PhotoStatus.DOWNLOADED }.map { deleteStoragePhoto(it) }
    }

    private suspend fun deleteStoragePhoto(purchaseModel: PurchasePhotoModel) = try {
        storage.reference
            .child(purchaseModel.purchaseId)
            .child(purchaseModel.purchasePhotoId)
            .delete().await().toUnit()
    } catch (e: Exception) {
        logger.e(DeletePurchasePhotoRepository::class.java.name, e.message, e)
        throw e
    }

    private fun deleteLocalPhoto(purchaseModel: PurchasePhotoModel) = try {
        val uri = Uri.parse(purchaseModel.purchasePhotoUri)
        when (contentResolver.delete(uri, null, null)) {
            1 -> logger.i(DeletePurchasePhotoRepository::class.java.name, "delete success")
            else -> logger.i(DeletePurchasePhotoRepository::class.java.name, "delete error")
        }
        contentResolver.notifyChange(uri, null)
    } catch (securityException: SecurityException) {
        logger.e(
            DeletePurchasePhotoRepository::class.java.name,
            "securityException ${securityException.message}",
            securityException
        )
        throw securityException
    }
}
