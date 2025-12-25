package com.veles.purchase.data.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.veles.purchase.data.room.core.createPrimaryIDKey
import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PurchaseTableModel
import com.veles.purchase.domain.utill.emptyString

@Entity
class PurchaseTable(
    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: String = createPrimaryIDKey(),
    @ColumnInfo(name = "Text")
    val text: String,
    @ColumnInfo(name = "Count")
    val count: String,
    @ColumnInfo(name = "Check")
    val check: Boolean,
    @ColumnInfo(name = "TypeHistory")
    val typeHistory: HistoryType,
    @ColumnInfo(name = "Time")
    val time: Long,
    @ColumnInfo(name = "Price")
    val price: String = emptyString(),
    @ColumnInfo(name = "CollectionId")
    val collectionId: String = emptyString()
)

fun PurchaseTableModel.toPurchaseTable() = PurchaseTable(
    id = id,
    text = text,
    count = count,
    check = check,
    typeHistory = typeHistory,
    time = time,
    price = price,
    collectionId = collectionId
)

fun PurchaseTable.toPurchaseTableModel() = PurchaseTableModel(
    id = id,
    text = text,
    count = count,
    check = check,
    typeHistory = typeHistory,
    time = time,
    price = price,
    collectionId = collectionId
)
