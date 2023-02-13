package com.veles.purchase.data.repository.sku

import com.veles.purchase.data.room.dao.SkuPhotoDAO
import com.veles.purchase.data.room.table.toSkuPhotoModel
import com.veles.purchase.domain.model.SkuPhotoModel
import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
import javax.inject.Inject

class SkuPhotoRepositoryImpl @Inject constructor(
    private val skuPhotoDAO: SkuPhotoDAO
) : SkuPhotoRepository {

    override suspend fun getSkuPhotoModelList(skuId: String): List<SkuPhotoModel> =
        skuPhotoDAO.getSkuPhotoEntityList(skuId).map { it.toSkuPhotoModel() }

    override suspend fun deletePhoto(skuPhotoId: String) =
        skuPhotoDAO.deleteSkuPhotoEntityWithPhotoId(skuPhotoId)
}
