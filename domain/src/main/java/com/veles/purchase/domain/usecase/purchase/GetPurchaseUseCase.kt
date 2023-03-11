package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import javax.inject.Inject

class GetPurchaseUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) {

    suspend operator fun invoke(
        collectionPurchaseId: String?,
        purchaseCreateId: String?
    ): PurchaseModel? = when {
        collectionPurchaseId.isNullOrEmpty() || purchaseCreateId.isNullOrEmpty() -> null
        else -> purchaseRepository.getPurchase(
            collectionPurchaseId,
            purchaseCreateId
        )
    }
}
