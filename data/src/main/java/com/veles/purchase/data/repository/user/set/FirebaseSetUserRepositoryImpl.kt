package com.veles.purchase.data.repository.user.set

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.USER_PURCHASE
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.user.set.FirebaseSetUserRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class FirebaseSetUserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val dataStore: DataStore
) : FirebaseSetUserRepository {

    override suspend fun apiFirebaseFirestore(userInfo: UserPurchaseModel): Boolean =
        suspendCancellableCoroutineWithTimeout(TimeUnit.SECONDS.toMillis(5.toLong())) { continuation ->
            firebaseFirestore.collection(COLLECTION_DATABASE)
                .document(EnvironmentConfig.DB_KEY)
                .collection(USER_PURCHASE)
                .document(userInfo.uid)
                .set(userInfo.copy(fcmToken = dataStore.getFCMToken()))
                .addOnCompleteListener {
                    continuation.resume(it.isSuccessful)
                }
                .addOnFailureListener {
                    if (continuation.isCancelled.not()) {
                        continuation.resumeWithException(
                            it
                        )
                    }
                }
        }
}
