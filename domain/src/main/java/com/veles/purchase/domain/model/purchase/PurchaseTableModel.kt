package com.veles.purchase.domain.model.purchase

import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.utill.createPrimaryIDKey
import com.veles.purchase.domain.utill.emptyString
import java.util.Calendar

class PurchaseTableModel(
    val id: String = createPrimaryIDKey(),
    val text: String,
    val count: String,
    val check: Boolean,
    val typeHistory: HistoryType,
    val time: Long,
    val price: String = emptyString(),
    val collectionId: String = emptyString()
)

fun PurchaseModel.createPurchaseTable(typeHistory: HistoryType): PurchaseTableModel =
    PurchaseTableModel(
        text = text,
        count = count,
        check = check,
        typeHistory = typeHistory,
        time = Calendar.getInstance().timeInMillis,
        price = price
    )
