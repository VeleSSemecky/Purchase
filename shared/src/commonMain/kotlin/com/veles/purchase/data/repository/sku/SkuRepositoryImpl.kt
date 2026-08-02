package com.veles.purchase.data.repository.sku

import com.veles.purchase.data.room.dao.SkuDAO
import com.veles.purchase.data.room.table.toSkuEntity
import com.veles.purchase.data.room.table.toSkuItemEntity
import com.veles.purchase.data.room.table.toSkuItemModel
import com.veles.purchase.data.room.table.toSkuModel
import com.veles.purchase.data.room.table.toSkuPhotoEntity
import com.veles.purchase.data.room.table.toSkuSumMonthModel
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.model.SkuPhotoModel
import com.veles.purchase.domain.model.SkuSumMonthModel
import com.veles.purchase.domain.repository.sku.SkuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SkuRepositoryImpl(private val skuDAO: SkuDAO) : SkuRepository {

    override suspend fun getSkuModel(skuId: String): SkuModel? =
        skuDAO.getSkuEntity(skuId)?.toSkuModel()?.copy(
            items = skuDAO.getSkuItems(skuId).map { it.toSkuItemModel() }
        )

    override suspend fun getSkuEntityList(): List<SkuModel> =
        skuDAO.getSkuEntityList().map { it.toSkuModel() }

    override fun getSkuEntityListFlow(): Flow<List<SkuModel>> =
        skuDAO.getSkuEntityListFlow().map { list -> list.map { it.toSkuModel() } }

    override suspend fun insert(skuModel: SkuModel, skuPhotoModelList: List<SkuPhotoModel>) {
        val skuEntity = skuModel.toSkuEntity()
        val skuPhotoEntityList = skuPhotoModelList.map { it.toSkuPhotoEntity() }
        val skuItemEntityList = skuModel.items.map { it.toSkuItemEntity(skuModel.skuId) }
        skuDAO.insert(skuEntity, skuPhotoEntityList, skuItemEntityList)
    }

    override suspend fun delete(id: String) = skuDAO.delete(id)

    override suspend fun getSkuSumMonthList(year: Int, month: Int): List<SkuSumMonthModel> =
        skuDAO.getSkuSumMonthList(month, year.toString())
            .sortedByDescending { it.skuMonth }
            .map { it.toSkuSumMonthModel() }
}
