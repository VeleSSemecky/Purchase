package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.SetCollectionPurchaseRepository
import javax.inject.Inject

class SetCollectionPurchaseUseCase @Inject constructor(
    private val setCollectionPurchaseRepository: SetCollectionPurchaseRepository
) {

    suspend operator fun invoke(purchaseModel: PurchaseCollectionModel) =
        setCollectionPurchaseRepository.setCollectionPurchase(purchaseModel)
}
