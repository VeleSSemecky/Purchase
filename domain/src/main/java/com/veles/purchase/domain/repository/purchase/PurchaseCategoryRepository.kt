package com.veles.purchase.domain.repository.purchase

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel

interface PurchaseCategoryRepository {

    suspend fun setPurchaseCategory(
        purchaseCollection: PurchaseCollectionModel
    )
}
