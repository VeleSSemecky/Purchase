package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetPurchasesUseCase(private val purchaseRepository: PurchaseRepository) {

    operator fun invoke(
        collectionId: String,
        search: String
    ): Flow<List<PurchaseModel>> = flow {
        val normalizedSearch = search.trim()

        when {
            collectionId.isEmpty() -> emit(emptyList())
            normalizedSearch.isEmpty() -> emitAll(purchaseRepository.getPurchaseFlow(collectionId))
            else -> {
                emitAll(
                    purchaseRepository.getPurchaseFlow(collectionId).map { purchases ->
                        purchases.filter { purchase ->
                            purchase.text.contains(normalizedSearch, ignoreCase = true)
                        }
                    }
                )
            }
        }
    }
}
