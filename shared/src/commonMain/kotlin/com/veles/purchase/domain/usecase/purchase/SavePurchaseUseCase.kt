package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.createPurchaseTable
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository

class SavePurchaseUseCase(private val purchaseRepository: PurchaseRepository, private val historyRepository: HistoryRepository) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String,
        historyType: HistoryType = HistoryType.ADD
    ): Result<Unit> = runCatching {
        purchaseRepository.setPurchase(purchaseModel, purchaseCollectionId)
        historyRepository.insert(purchaseModel.createPurchaseTable(historyType, purchaseCollectionId))
    }
}
