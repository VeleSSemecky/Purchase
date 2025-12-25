@file:OptIn(ExperimentalTime::class)

package com.veles.purchase.domain.model.purchase

import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.utill.createPrimaryIDKey
import com.veles.purchase.domain.utill.emptyString
import kotlin.time.ExperimentalTime

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

fun PurchaseModel.createPurchaseTable(
    typeHistory: HistoryType,
    purchaseCollectionId: String
): PurchaseTableModel = PurchaseTableModel(
    id = createId,
    text = text,
    count = count,
    check = isChecked,
    typeHistory = typeHistory,
    time = kotlin.time.Clock.System.now().toEpochMilliseconds(),
    price = price,
    collectionId = purchaseCollectionId
)
