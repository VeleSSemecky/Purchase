package com.veles.purchase.domain.repository.collection.set

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel

interface FirebaseSetCollectionPurchaseRepository {
    suspend fun apiFirebaseFirestore(purchaseCollection: PurchaseCollectionModel): Boolean
}
