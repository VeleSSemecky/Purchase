package com.veles.purchase.domain.usecase.sku

import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.repository.sku.SkuRepository
import javax.inject.Inject

class GetSkuUseCase @Inject constructor(
    private val skuDAO: SkuRepository
) {

    suspend fun getSkuModel(skuId: String): SkuModel? = skuDAO.getSkuModel(skuId)

    suspend fun getSkuModelList() = skuDAO.getSkuEntityList()
}
