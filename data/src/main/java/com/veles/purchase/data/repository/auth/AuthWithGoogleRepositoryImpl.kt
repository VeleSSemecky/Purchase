package com.veles.purchase.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.USER_PURCHASE
import com.veles.purchase.data.core.extensions.toUnit
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.data.model.user.createUserPurchase
import com.veles.purchase.data.model.user.toUserPurchaseModel
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class AuthWithGoogleRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val dataStore: DataStore
) : AuthWithGoogleRepository {

    private fun getUserPurchase() = firebaseFirestore
        .collection(EnvironmentConfig.COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(USER_PURCHASE)

    override suspend fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = firebaseAuth.signInWithCredential(credential).await()
        val user: FirebaseUser =
            authResult.user ?: throw IllegalArgumentException("FirebaseUser is null")
        setUser(
            user.createUserPurchase()
                .toUserPurchaseModel()
                .copy(fcmToken = dataStore.getFCMToken())
        )
    }

    private suspend fun setUser(userInfo: UserPurchaseModel) =
        suspendCancellableCoroutineWithTimeout {
            getUserPurchase()
                .document(userInfo.uid)
                .set(userInfo)
                .await().toUnit()
        }
}
