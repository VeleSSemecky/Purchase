package com.veles.purchase.domain.repository.collection.get

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import kotlinx.coroutines.flow.Flow

interface FirebaseCollectionPurchaseRepository {

    suspend fun apiFirebaseFirestorePurchase(): Flow<List<PurchaseCollectionModel>>

    suspend fun apiFirebaseFirestoreDeletePurchaseCollection(
        purchaseCollection: PurchaseCollectionModel
    ): Boolean

    suspend fun apiFirebaseFirestorePurchase(id: String): PurchaseCollectionModel
}
