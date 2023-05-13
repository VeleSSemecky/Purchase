package com.veles.purchase.data.repository.user.token

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.FCM_TOKEN
import com.veles.purchase.config.EnvironmentConfig.USER_PURCHASE
import com.veles.purchase.domain.repository.user.FirebaseMessageTokenRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class FirebaseMessageTokenRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseMessageTokenRepository {

    override suspend fun sendMessageToken(userUid: String, messageToken: String): Void =
        firebaseFirestore.collection(COLLECTION_DATABASE)
            .document(EnvironmentConfig.DB_KEY)
            .collection(USER_PURCHASE)
            .document(userUid)
            .update(FCM_TOKEN, messageToken).await()
}
