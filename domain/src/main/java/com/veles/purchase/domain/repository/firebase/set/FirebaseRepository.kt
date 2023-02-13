package com.veles.purchase.domain.repository.firebase.set

import com.veles.purchase.domain.model.purchase.PurchaseModel

interface FirebaseRepository {

    suspend fun apiFirebaseFirestore(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ): Boolean
}
