package com.veles.purchase.domain.repository.sku

import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.model.SkuPhotoModel
import com.veles.purchase.domain.model.SkuSumMonthModel

interface SkuRepository {
    suspend fun getSkuModel(skuId: String): SkuModel?
    suspend fun getSkuEntityList(): List<SkuModel>
    suspend fun insert(skuModel: SkuModel, skuPhotoModelList: List<SkuPhotoModel>)
    suspend fun delete(id: String)
    suspend fun getSkuSumMonthList(year: Int, month: Int): List<SkuSumMonthModel>
}
