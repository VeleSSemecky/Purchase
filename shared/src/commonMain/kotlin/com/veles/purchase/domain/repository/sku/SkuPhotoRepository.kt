package com.veles.purchase.domain.repository.sku

import com.veles.purchase.domain.model.SkuPhotoModel

interface SkuPhotoRepository {
    suspend fun getSkuPhotoModelList(skuId: String): List<SkuPhotoModel>
    suspend fun deletePhoto(skuPhotoId: String)
}
