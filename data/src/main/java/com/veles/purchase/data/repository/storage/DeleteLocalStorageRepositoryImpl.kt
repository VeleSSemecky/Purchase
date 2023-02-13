package com.veles.purchase.data.repository.storage

import android.content.ContentResolver
import android.net.Uri
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.DeleteLocalStorageRepository
import javax.inject.Inject

class DeleteLocalStorageRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val logger: Logger
) : DeleteLocalStorageRepository {

    override suspend fun deletePhoto(purchaseModel: PurchasePhotoModel): Boolean {
        return try {
            val uri = Uri.parse(purchaseModel.purchasePhotoUri)
            val result = when (contentResolver.delete(uri, null, null)) {
                1 -> true
                else -> false
            }
            contentResolver.notifyChange(uri, null)
            result
        } catch (securityException: SecurityException) {
            logger.e(
                DeleteLocalStorageRepository::class.java.name,
                "securityException ${securityException.message}",
                securityException
            )
            false
        }
    }
}
