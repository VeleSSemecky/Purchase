package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository

class CheckPurchaseUseCase(
    private val purchaseRepository: PurchaseRepository
) {

    suspend operator fun invoke(
        purchaseCollectionId: String,
        purchaseModel: PurchaseModel
    ) {
        if (purchaseCollectionId.isEmpty()) return

        purchaseRepository.setPurchase(
            purchaseModel.copy(isChecked = purchaseModel.isChecked.not()),
            purchaseCollectionId
        )
    }
}

