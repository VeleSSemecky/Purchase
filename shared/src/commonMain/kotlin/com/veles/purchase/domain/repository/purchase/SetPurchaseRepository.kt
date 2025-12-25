package com.veles.purchase.domain.repository.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel

interface SetPurchaseRepository {

    suspend fun setPurchase(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    )
}
