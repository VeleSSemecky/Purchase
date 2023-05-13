package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.GetPurchaseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetPurchasesUseCase @Inject constructor(
    private val getPurchaseRepository: GetPurchaseRepository
) {

    operator fun invoke(
        modelCollectionPurchaseId: String,
        search: String
    ): Flow<List<PurchaseModel>> = when {
        modelCollectionPurchaseId.isEmpty() -> emptyFlow()
        else -> getPurchaseRepository.getLisPurchases(
            modelCollectionPurchaseId,
            search
        )
    }
}
