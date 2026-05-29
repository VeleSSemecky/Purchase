package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseTableModel
import com.veles.purchase.domain.repository.history.HistoryRepository
import kotlinx.coroutines.flow.Flow

class GetPurchaseHistoryUseCase(private val historyRepository: HistoryRepository) {

    operator fun invoke(collectionId: String): Flow<List<PurchaseTableModel>> =
        historyRepository.getHistoryFlow(collectionId)
}
