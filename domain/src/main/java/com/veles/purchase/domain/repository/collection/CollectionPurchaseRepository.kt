package com.veles.purchase.domain.repository.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel

interface CollectionPurchaseRepository {

    suspend fun setCollectionPurchase(purchaseCollection: PurchaseCollectionModel)
}
