package com.veles.purchase.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.USER_PURCHASE
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.data.model.user.createUserPurchase
import com.veles.purchase.data.model.user.toUserPurchaseModel
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.tasks.await

class AuthWithGoogleRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val dataStore: DataStore
) : AuthWithGoogleRepository {

    override suspend fun firebaseAuthWithGoogle(idToken: String?): Boolean {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = firebaseAuth.signInWithCredential(credential).await()
        val user: FirebaseUser = authResult.user ?: return false
        return apiFirebaseFirestore(user.createUserPurchase().toUserPurchaseModel())
    }

    override suspend fun apiFirebaseFirestore(userInfo: UserPurchaseModel): Boolean =
        suspendCancellableCoroutineWithTimeout(TimeUnit.SECONDS.toMillis(5.toLong())) { continuation ->
            firebaseFirestore
                .collection(EnvironmentConfig.COLLECTION_DATABASE)
                .document(EnvironmentConfig.DB_KEY)
                .collection(USER_PURCHASE)
                .document(userInfo.uid)
                .set(userInfo.copy(fcmToken = dataStore.getFCMToken()))
                .addOnCompleteListener { continuation.resume(it.isSuccessful) }
                .addOnFailureListener {
                    if (continuation.isCancelled.not()) {
                        continuation.resumeWithException(
                            it
                        )
                    }
                }
        }
}
