package com.veles.purchase.domain.repository.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel

interface DeleteCollectionPurchaseRepository {

    suspend fun deletePurchaseCollection(purchaseCollection: PurchaseCollectionModel)
}
