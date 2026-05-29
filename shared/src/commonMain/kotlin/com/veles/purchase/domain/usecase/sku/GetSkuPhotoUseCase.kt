package com.veles.purchase.domain.usecase.sku
import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
class GetSkuPhotoUseCase(private val skuPhotoRepository: SkuPhotoRepository) {
    suspend operator fun invoke(skuId: String) =
        skuPhotoRepository.getSkuPhotoModelList(skuId)
}
