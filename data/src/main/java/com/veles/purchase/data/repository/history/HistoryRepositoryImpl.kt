package com.veles.purchase.data.repository.history

import com.veles.purchase.data.room.dao.PurchaseDAO
import com.veles.purchase.data.room.table.toPurchaseTable
import com.veles.purchase.data.room.table.toPurchaseTableModel
import com.veles.purchase.domain.model.purchase.PurchaseTableModel
import com.veles.purchase.domain.repository.history.HistoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepositoryImpl @Inject constructor(
    private val purchaseDAO: PurchaseDAO
) : HistoryRepository {

    override suspend fun insert(purchaseTable: PurchaseTableModel) {
        purchaseDAO.insert(purchaseTable.toPurchaseTable())
    }

    override suspend fun getHistory(): List<PurchaseTableModel> =
        purchaseDAO.getListPurchaseTables().map { it.toPurchaseTableModel() }
}
