package com.veles.purchase.data.repository.user

import com.veles.purchase.config.EnvironmentConfig.FCM_TOKEN
import com.veles.purchase.data.extensions.userPurchase
import com.veles.purchase.domain.repository.user.FirebaseMessageTokenRepository
import dev.gitlive.firebase.firestore.FirebaseFirestore

/**
 * Firebase KMP implementation of FirebaseMessageTokenRepository
 * Manages FCM tokens in Firestore
 */
class FirebaseMessageTokenRepositoryImpl(
    private val firestore: FirebaseFirestore
) : FirebaseMessageTokenRepository {

    override suspend fun sendMessageToken(userUid: String, messageToken: String) {
        // Use set with merge to update the FCM token field
        firestore.userPurchase
            .document(userUid)
            .set(mapOf(FCM_TOKEN to messageToken), merge = true)
    }
}
