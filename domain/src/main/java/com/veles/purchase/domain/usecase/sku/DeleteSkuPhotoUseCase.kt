package com.veles.purchase.domain.usecase.sku

import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
import javax.inject.Inject

class DeleteSkuPhotoUseCase @Inject constructor(
    private val skuPhotoRepository: SkuPhotoRepository
) {

    suspend operator fun invoke(skuPhotoId: String) = skuPhotoRepository.deletePhoto(skuPhotoId)
}
