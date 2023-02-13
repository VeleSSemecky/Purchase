package com.veles.purchase.data.room.table

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.veles.purchase.data.room.core.createPrimaryIDKey
import com.veles.purchase.domain.model.SkuPhotoModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class SkuPhotoEntity(
    @PrimaryKey
    @ColumnInfo(name = "SkuPhotoId")
    val skuPhotoId: String = createPrimaryIDKey(),
    @ColumnInfo(name = "SkuPhotoUri")
    val skuPhotoUri: Uri = Uri.EMPTY,
    @ColumnInfo(name = "SkuId")
    val skuId: String
) : Parcelable

fun SkuPhotoEntity.toSkuPhotoModel(): SkuPhotoModel =
    SkuPhotoModel(
        skuPhotoId = skuPhotoId,
        skuPhotoUri = skuPhotoUri.toString(),
        skuId = skuId
    )

fun SkuPhotoModel.toSkuPhotoEntity(): SkuPhotoEntity =
    SkuPhotoEntity(
        skuPhotoId = skuPhotoId,
        skuPhotoUri = Uri.parse(skuPhotoUri),
        skuId = skuId
    )
