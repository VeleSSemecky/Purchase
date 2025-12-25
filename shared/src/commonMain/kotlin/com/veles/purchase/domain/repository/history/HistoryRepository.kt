package com.veles.purchase.domain.repository.history

import com.veles.purchase.domain.model.purchase.PurchaseTableModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun insert(purchaseTable: PurchaseTableModel)
    suspend fun getHistory(): List<PurchaseTableModel>
    fun getHistoryFlow(collectionId: String): Flow<List<PurchaseTableModel>>
}
