package com.veles.purchase.domain.repository.storage

interface DeletePurchaseRepository {

    suspend fun deletePurchase(
        purchaseCreateId: String,
        purchaseCollectionId: String
    )
}
