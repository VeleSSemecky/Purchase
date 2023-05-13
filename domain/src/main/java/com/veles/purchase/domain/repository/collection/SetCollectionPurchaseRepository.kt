package com.veles.purchase.domain.repository.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel

interface SetCollectionPurchaseRepository {

    suspend fun setCollectionPurchase(purchaseCollection: PurchaseCollectionModel)
}
