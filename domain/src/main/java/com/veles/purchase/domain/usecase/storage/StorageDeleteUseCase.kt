package com.veles.purchase.domain.usecase.storage

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.DeletePurchasePhotoRepository
import javax.inject.Inject

class StorageDeleteUseCase @Inject constructor(
    private val deletePurchasePhotoRepository: DeletePurchasePhotoRepository
) {

    suspend fun deleteStorage(purchasePhotoModel: PurchasePhotoModel) {
        deletePurchasePhotoRepository.deletePhoto(purchasePhotoModel)
    }
}
