package com.veles.purchase.data.repository.user.token

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.FCM_TOKEN
import com.veles.purchase.config.EnvironmentConfig.USER_PURCHASE
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.user.token.FirebaseSetFcmTokenUserRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class FirebaseSetFcmTokenUserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseSetFcmTokenUserRepository {

    override suspend fun apiFirebaseFirestore(userInfo: UserPurchaseModel): Boolean =
        suspendCancellableCoroutineWithTimeout(TimeUnit.SECONDS.toMillis(5.toLong())) { continuation ->
            firebaseFirestore.collection(COLLECTION_DATABASE)
                .document(EnvironmentConfig.DB_KEY)
                .collection(USER_PURCHASE)
                .document(userInfo.uid)
                .update(FCM_TOKEN, userInfo.fcmToken)
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
