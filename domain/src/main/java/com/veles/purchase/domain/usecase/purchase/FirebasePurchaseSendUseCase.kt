package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.SetPurchaseRepository
import javax.inject.Inject

class FirebasePurchaseSendUseCase @Inject constructor(
    private val setPurchaseRepository: SetPurchaseRepository
) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) = setPurchaseRepository.setPurchase(
        purchaseModel,
        purchaseCollectionId
    )
}
