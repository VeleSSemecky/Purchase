package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseTableModel
import com.veles.purchase.domain.repository.history.HistoryRepository
import javax.inject.Inject

class FirebasePurchaseGetHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(): List<PurchaseTableModel> = historyRepository.getHistory()
}
