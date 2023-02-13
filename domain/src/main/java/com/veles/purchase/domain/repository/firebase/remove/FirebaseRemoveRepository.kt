package com.veles.purchase.domain.repository.firebase.remove

interface FirebaseRemoveRepository {

    suspend fun apiFirebaseRemove(
        purchaseCreateId: String,
        purchaseCollectionId: String
    ): Boolean
}
