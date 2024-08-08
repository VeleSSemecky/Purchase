package com.veles.purchase.data.extensions

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig

val FirebaseFirestore.collectionPurchase
    get() = collection(EnvironmentConfig.COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(EnvironmentConfig.COLLECTION_PURCHASE)

val FirebaseFirestore.userPurchase
    get() = collection(EnvironmentConfig.COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(EnvironmentConfig.USER_PURCHASE)

fun FirebaseFirestore.purchase(collectionId: String) = collectionPurchase
    .document(collectionId)
    .collection(EnvironmentConfig.PURCHASE)
