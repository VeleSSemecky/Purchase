package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.CollectionRepository

class SavePurchaseCategoryUseCase(private val collectionRepository: CollectionRepository) {

    suspend operator fun invoke(
        purchaseCollectionModel: PurchaseCollectionModel,
        newPurchaseCategoryModel: List<PurchaseCategoryModel>
    ) = runCatching {
        collectionRepository.saveCollection(
            purchaseCollectionModel.copy(
                categoryModels = newPurchaseCategoryModel
            )
        )
    }
}
