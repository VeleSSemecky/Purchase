package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.repository.collection.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCollectionPurchaseCategoryUseCase(private val collectionRepository: CollectionRepository) {

    operator fun invoke(purchaseCollectionId: String): Flow<List<PurchaseCategoryModel>> =
        collectionRepository.getCollections().map { collections ->
            collections.find { it.id == purchaseCollectionId }?.categoryModels ?: emptyList()
        }
}
