package com.veles.purchase.domain.usecase.storage

import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.DeleteFirebaseStorageRepository
import com.veles.purchase.domain.repository.storage.DeleteLocalStorageRepository
import javax.inject.Inject

class StorageDeleteUseCase @Inject constructor(
    private val deleteFirebaseStorageRepository: DeleteFirebaseStorageRepository,
    private val deleteLocalStorageRepository: DeleteLocalStorageRepository
) {

    suspend fun deleteStorage(purchasePhotoModel: PurchasePhotoModel): Boolean {
        return when (purchasePhotoModel.status) {
            PhotoStatus.LOCAL -> deleteLocalStorageRepository.deletePhoto(purchasePhotoModel)
            PhotoStatus.DOWNLOADED -> deleteFirebaseStorageRepository.deletePhoto(
                purchasePhotoModel
            )
        }
    }
}
