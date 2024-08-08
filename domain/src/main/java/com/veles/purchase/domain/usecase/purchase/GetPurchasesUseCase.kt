package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetPurchasesUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) {

    operator fun invoke(
        collectionId: String,
        search: String
    ): Flow<List<PurchaseModel>> = flow {
        when {
            collectionId.isEmpty() -> emit(emptyList())
            search.isEmpty() -> emitAll(purchaseRepository.getPurchaseFlow(collectionId))
            else -> emitAll(
                purchaseRepository.getPurchaseFlow(collectionId).map { list ->
                    val searchList = purchaseRepository.getSearchPurchaseList(collectionId, search)
                    searchList + list.filter { !searchList.contains(it) }
                }
            )
        }
    }
}
