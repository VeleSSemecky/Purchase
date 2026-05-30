package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.SetPurchasePhotoRepository

class UploadPurchasePhotosUseCase(
    private val setPurchasePhotoRepository: SetPurchasePhotoRepository
) {

    suspend operator fun invoke(photos: List<PurchasePhotoModel>): List<PurchasePhotoModel> =
        setPurchasePhotoRepository.setPurchasePhotos(photos)
}
