package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseTableModel
import com.veles.purchase.domain.repository.history.HistoryRepository

class SetPurchaseHistoryUseCase(private val historyRepository: HistoryRepository) {

    suspend operator fun invoke(purchaseTable: PurchaseTableModel) =
        historyRepository.insert(purchaseTable)
}
