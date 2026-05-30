package com.veles.purchase.domain.usecase.sku

import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.model.SkuPhotoModel
import com.veles.purchase.domain.repository.sku.SkuRepository

class SetSkuUseCase(private val skuRepository: SkuRepository) {

    suspend operator fun invoke(skuEntity: SkuModel, skuPhotoEntityList: List<SkuPhotoModel>): Result<Unit> =
        runCatching { skuRepository.insert(skuEntity, skuPhotoEntityList) }
}
