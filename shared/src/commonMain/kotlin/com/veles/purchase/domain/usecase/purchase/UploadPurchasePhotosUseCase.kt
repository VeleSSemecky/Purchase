package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.SetPurchasePhotoRepository
import kotlinx.coroutines.CancellationException

class UploadPurchasePhotosUseCase(
    private val setPurchasePhotoRepository: SetPurchasePhotoRepository
) {

    suspend operator fun invoke(photos: List<PurchasePhotoModel>): Result<List<PurchasePhotoModel>> =
        runCatching { setPurchasePhotoRepository.setPurchasePhotos(photos) }
            .also { it.exceptionOrNull()?.let { e -> if (e is CancellationException) throw e } }
}
