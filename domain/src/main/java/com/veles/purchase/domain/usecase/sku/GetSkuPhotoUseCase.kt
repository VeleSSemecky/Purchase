package com.veles.purchase.domain.usecase.sku

import com.veles.purchase.domain.model.SkuPhotoModel
import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
import javax.inject.Inject

class GetSkuPhotoUseCase @Inject constructor(
    private val skuPhotoRepository: SkuPhotoRepository
) {

    suspend operator fun invoke(skuId: String): List<SkuPhotoModel> =
        skuPhotoRepository.getSkuPhotoModelList(skuId)
}
