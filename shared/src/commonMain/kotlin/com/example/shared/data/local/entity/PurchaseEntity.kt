package com.example.shared.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shared.domain.model.purchase.PurchaseModel

@Entity(tableName = "purchase_table")
data class PurchaseEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "count")
    val count: String,

    @ColumnInfo(name = "is_checked")
    val isChecked: Boolean,

    @ColumnInfo(name = "time")
    val time: Long,

    @ColumnInfo(name = "price")
    val price: String,

    @ColumnInfo(name = "collection_id")
    val collectionId: String
)

// Converter functions
fun PurchaseEntity.toDomainModel() = PurchaseModel(
    id = id,
    text = text,
    count = count,
    isChecked = isChecked,
    time = time,
    price = price,
    collectionId = collectionId
)

fun PurchaseModel.toEntity() = PurchaseEntity(
    id = id,
    text = text,
    count = count,
    isChecked = isChecked,
    time = time,
    price = price,
    collectionId = collectionId
)
