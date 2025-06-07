package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseTableModel
import com.veles.purchase.domain.repository.history.HistoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPurchaseHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    operator fun invoke(collectionId: String): Flow<List<PurchaseTableModel>> =
        historyRepository.getHistoryFlow(collectionId)
}
