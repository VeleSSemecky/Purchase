package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetPurchasesUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) {

    operator fun invoke(
        modelCollectionPurchaseId: String,
        search: String
    ): Flow<List<PurchaseModel>> = when {
        modelCollectionPurchaseId.isEmpty() -> emptyFlow()
        else -> purchaseRepository.getFlowPurchase(
            modelCollectionPurchaseId,
            search
        )
    }
}
