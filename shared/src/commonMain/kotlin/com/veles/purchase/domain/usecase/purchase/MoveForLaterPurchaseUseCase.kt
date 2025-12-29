package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository

class MoveForLaterPurchaseUseCase(
    private val purchaseRepository: PurchaseRepository
) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) {
        purchaseRepository.deletePurchase(
            purchaseModel.createId,
            purchaseCollectionId
        )
    }
}

