package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.CollectionPurchaseRepository
import javax.inject.Inject

class SetCollectionPurchaseUseCase @Inject constructor(
    private val collectionPurchaseRepository: CollectionPurchaseRepository
) {

    suspend operator fun invoke(purchaseModel: PurchaseCollectionModel) =
        collectionPurchaseRepository.setCollectionPurchase(purchaseModel)
}
