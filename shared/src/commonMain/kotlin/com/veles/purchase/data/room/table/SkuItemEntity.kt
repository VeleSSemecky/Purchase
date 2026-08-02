package com.veles.purchase.data.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.veles.purchase.data.room.core.createPrimaryIDKey
import com.veles.purchase.domain.model.SkuItemModel
import com.veles.purchase.domain.utill.emptyString

/**
 * A product line item that belongs to a [SkuEntity] (the purchase / receipt total).
 *
 * Linked to the parent via [skuId]. When the parent purchase is deleted, its
 * items are removed too (CASCADE).
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SkuEntity::class,
            parentColumns = ["SkuId"],
            childColumns = ["SkuId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("SkuId")]
)
data class SkuItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "SkuItemId")
    val skuItemId: String = createPrimaryIDKey(),
    @ColumnInfo(name = "SkuId")
    val skuId: String,
    @ColumnInfo(name = "Name")
    val name: String = emptyString(),
    @ColumnInfo(name = "Quantity")
    val quantity: String = emptyString(),
    @ColumnInfo(name = "Price")
    val price: String = emptyString(),
    @ColumnInfo(name = "TaxRate")
    val taxRate: String = emptyString()
)

fun SkuItemEntity.toSkuItemModel(): SkuItemModel =
    SkuItemModel(
        skuItemId = skuItemId,
        skuId = skuId,
        name = name,
        quantity = quantity,
        price = price,
        taxRate = taxRate
    )

fun SkuItemModel.toSkuItemEntity(parentSkuId: String): SkuItemEntity =
    SkuItemEntity(
        skuItemId = skuItemId,
        skuId = parentSkuId,
        name = name,
        quantity = quantity,
        price = price,
        taxRate = taxRate
    )

