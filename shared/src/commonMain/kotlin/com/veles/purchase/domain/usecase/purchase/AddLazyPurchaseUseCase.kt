package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.createPurchaseTable
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository

class AddLazyPurchaseUseCase(
    private val purchaseRepository: PurchaseRepository,
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) {
        purchaseRepository.setPurchase(purchaseModel, purchaseCollectionId)

        historyRepository.insert(
            purchaseModel.createPurchaseTable(
                HistoryType.ADD,
                purchaseCollectionId
            )
        )
    }
}

