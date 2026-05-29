package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.CollectionRepository

class DeletePurchaseCollectionUseCase(private val collectionRepository: CollectionRepository) {

    suspend operator fun invoke(purchaseCollection: PurchaseCollectionModel) =
        collectionRepository.deleteCollection(purchaseCollection)
}
