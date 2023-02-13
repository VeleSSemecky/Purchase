package com.veles.purchase.data.repository.firebase.remove

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_PURCHASE
import com.veles.purchase.config.EnvironmentConfig.PURCHASE
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.repository.firebase.remove.FirebaseRemoveRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class FirebaseRemoveRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseRemoveRepository {

    override suspend fun apiFirebaseRemove(
        purchaseCreateId: String,
        purchaseCollectionId: String
    ): Boolean =
        suspendCancellableCoroutineWithTimeout(
            TimeUnit.SECONDS.toMillis(5.toLong())
        ) { continuation ->
            firebaseFirestore.collection(COLLECTION_DATABASE)
                .document(EnvironmentConfig.DB_KEY)
                .collection(COLLECTION_PURCHASE)
                .document(purchaseCollectionId)
                .collection(PURCHASE)
                .document(purchaseCreateId)
                .delete()
                .addOnSuccessListener {
                    if (continuation.isCancelled.not()) continuation.resume(true)
                }
                .addOnFailureListener {
                    if (continuation.isCancelled.not()) continuation.resumeWithException(it)
                }
        }
}
