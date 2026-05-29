package com.veles.purchase.domain.usecase.sku

import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.repository.sku.SkuRepository

class GetSkuUseCase(private val skuRepository: SkuRepository) {

    suspend fun getSkuModel(skuId: String): SkuModel? = skuRepository.getSkuModel(skuId)

    suspend fun getSkuModelList() = skuRepository.getSkuEntityList()
}
