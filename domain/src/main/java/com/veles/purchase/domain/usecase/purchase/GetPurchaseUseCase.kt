package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.GetPurchaseRepository
import javax.inject.Inject

class GetPurchaseUseCase @Inject constructor(
    private val getPurchaseRepository: GetPurchaseRepository
) {

    suspend operator fun invoke(
        collectionPurchaseId: String?,
        purchaseCreateId: String?
    ): PurchaseModel? = when {
        collectionPurchaseId.isNullOrEmpty() || purchaseCreateId.isNullOrEmpty() -> null
        else -> getPurchaseRepository.getPurchase(
            collectionPurchaseId,
            purchaseCreateId
        )
    }
}
