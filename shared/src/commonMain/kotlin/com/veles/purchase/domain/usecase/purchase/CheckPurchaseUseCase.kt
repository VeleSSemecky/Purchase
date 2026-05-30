package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.createPurchaseTable
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository

class CheckPurchaseUseCase(private val purchaseRepository: PurchaseRepository, private val historyRepository: HistoryRepository) {

    suspend operator fun invoke(
        purchaseCollectionId: String,
        purchaseModel: PurchaseModel
    ): Result<Unit> = runCatching {
        if (purchaseCollectionId.isEmpty()) return@runCatching

        val toggled = purchaseModel.copy(isChecked = !purchaseModel.isChecked)
        purchaseRepository.setPurchase(toggled, purchaseCollectionId)

        val historyType = if (toggled.isChecked) HistoryType.CHECK else HistoryType.UNCHECK
        historyRepository.insert(toggled.createPurchaseTable(historyType, purchaseCollectionId))
    }
}
