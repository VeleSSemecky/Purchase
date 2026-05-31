package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.CollectionRepository
import kotlinx.coroutines.CancellationException

class SetCollectionPurchaseUseCase(private val collectionRepository: CollectionRepository) {

    suspend operator fun invoke(purchaseModel: PurchaseCollectionModel): Result<Unit> =
        runCatching { collectionRepository.saveCollection(purchaseModel) }
            .also { it.exceptionOrNull()?.let { e -> if (e is CancellationException) throw e } }
}
