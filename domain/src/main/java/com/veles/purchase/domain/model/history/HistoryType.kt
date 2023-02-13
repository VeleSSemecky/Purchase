package com.veles.purchase.domain.model.history

import com.veles.purchase.domain.model.purchase.PurchaseModel

enum class HistoryType {
    CHECK,
    ADD,
    CHANGE,
    DELETE,
    UNCHECK
}

fun PurchaseModel.toHistoryType() = if (check) HistoryType.CHECK else HistoryType.UNCHECK
