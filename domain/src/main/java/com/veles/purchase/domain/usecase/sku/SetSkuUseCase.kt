package com.veles.purchase.domain.usecase.sku

import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.model.SkuPhotoModel
import com.veles.purchase.domain.repository.sku.SkuRepository
import javax.inject.Inject

class SetSkuUseCase @Inject constructor(
    private val skuDAO: SkuRepository
) {

    suspend operator fun invoke(skuEntity: SkuModel, skuPhotoEntityList: List<SkuPhotoModel>) =
        skuDAO.insert(skuEntity, skuPhotoEntityList)
}
