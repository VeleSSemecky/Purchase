package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import com.veles.purchase.domain.repository.purchase.SetPurchaseRepository
import javax.inject.Inject

class FirebasePurchaseSendUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) = purchaseRepository.setPurchase(
        purchaseModel,
        purchaseCollectionId
    )
}
