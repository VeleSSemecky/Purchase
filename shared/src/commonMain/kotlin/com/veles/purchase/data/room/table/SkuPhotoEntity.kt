package com.veles.purchase.data.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.veles.purchase.data.room.core.createPrimaryIDKey
import com.veles.purchase.domain.model.SkuPhotoModel

@Entity
data class SkuPhotoEntity(
    @PrimaryKey
    @ColumnInfo(name = "SkuPhotoId")
    val skuPhotoId: String = createPrimaryIDKey(),
    @ColumnInfo(name = "SkuPhotoUri")
    val skuPhotoUri: String = "", // Changed from Uri to String for KMP compatibility
    @ColumnInfo(name = "SkuId")
    val skuId: String
)

fun SkuPhotoEntity.toSkuPhotoModel(): SkuPhotoModel =
    SkuPhotoModel(
        skuPhotoId = skuPhotoId,
        skuPhotoUri = skuPhotoUri,
        skuId = skuId
    )

fun SkuPhotoModel.toSkuPhotoEntity(): SkuPhotoEntity =
    SkuPhotoEntity(
        skuPhotoId = skuPhotoId,
        skuPhotoUri = skuPhotoUri,
        skuId = skuId
    )
