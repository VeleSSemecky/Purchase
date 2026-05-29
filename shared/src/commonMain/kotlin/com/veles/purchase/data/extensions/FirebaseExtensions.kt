package com.veles.purchase.data.extensions

import com.veles.purchase.config.EnvironmentConfig
import dev.gitlive.firebase.firestore.FirebaseFirestore

/**
 * Extension property to get the collection purchase reference
 */
val FirebaseFirestore.collectionPurchase
    get() = collection(EnvironmentConfig.COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(EnvironmentConfig.COLLECTION_PURCHASE)

/**
 * Extension property to get the user purchase reference
 */
val FirebaseFirestore.userPurchase
    get() = collection(EnvironmentConfig.COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(EnvironmentConfig.USER_PURCHASE)

/**
 * Extension function to get a specific purchase collection
 */
fun FirebaseFirestore.purchase(collectionId: String) = collectionPurchase
    .document(collectionId)
    .collection(EnvironmentConfig.PURCHASE)
