package com.veles.purchase.domain.repository.sku

import com.veles.purchase.domain.model.SkuPhotoModel
import com.veles.purchase.domain.utill.createPrimaryIDKey
import kotlinx.coroutines.delay

class MockSkuPhotoRepository : SkuPhotoRepository {

    private val mockPhotos = mutableMapOf<String, MutableList<SkuPhotoModel>>()

    override suspend fun getSkuPhotoModelList(skuId: String): List<SkuPhotoModel> {
        delay(200)
        return mockPhotos[skuId]?.toList() ?: emptyList()
    }

    override suspend fun deletePhoto(skuPhotoId: String) {
        delay(150)
        mockPhotos.values.forEach { photoList ->
            photoList.removeAll { it.skuPhotoId == skuPhotoId }
        }
    }
}

