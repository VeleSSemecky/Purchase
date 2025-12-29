package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.CollectionRepository
import kotlinx.coroutines.flow.Flow

class FirebaseFirestorePurchaseCollectionUseCase(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(id: String?): PurchaseCollectionModel? =
        when (id) {
            null -> null
            else -> collectionRepository.getCollection(id)
        }

    operator fun invoke(): Flow<List<PurchaseCollectionModel>> =
        collectionRepository.getCollections()
}

