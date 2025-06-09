package com.veles.purchase.presentation.model.purchase

import com.veles.purchase.domain.model.purchase.PurchaseTableModel
import com.veles.purchase.domain.utill.createPrimaryIDKey
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.model.history.HistoryTypeUI
import com.veles.purchase.presentation.model.history.toHistoryTypeUI

data class PurchaseTableModelUI(
    val id: String = createPrimaryIDKey(),
    val text: String,
    val count: String,
    val check: Boolean,
    val typeHistory: HistoryTypeUI,
    val time: Long,
    val price: String = emptyString(),
    val collectionId: String = emptyString()
){
    companion object {

        val PREVIEW_STATE = PurchaseTableModelUI(
            text = "PurchaseTableModelUI",
            count = "2",
            check = true,
            typeHistory = HistoryTypeUI.DELETE,
            time = System.currentTimeMillis(),
            price = "123",
            collectionId = ""
        )
    }
}

fun PurchaseTableModel.toPurchaseTableModelUI() = PurchaseTableModelUI(
    id = id,
    text = text,
    count = count,
    check = check,
    typeHistory = typeHistory.toHistoryTypeUI(),
    time = time,
    price = price,
    collectionId = collectionId
)
