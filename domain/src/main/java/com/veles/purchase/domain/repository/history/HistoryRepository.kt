package com.veles.purchase.domain.repository.history

import com.veles.purchase.domain.model.purchase.PurchaseTableModel

interface HistoryRepository {
    suspend fun insert(purchaseTable: PurchaseTableModel)
    suspend fun getHistory(): List<PurchaseTableModel>
}
