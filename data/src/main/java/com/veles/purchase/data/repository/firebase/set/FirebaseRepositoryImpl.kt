package com.veles.purchase.data.repository.firebase.set

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_PURCHASE
import com.veles.purchase.config.EnvironmentConfig.DB_KEY
import com.veles.purchase.config.EnvironmentConfig.PURCHASE
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.firebase.set.FirebaseRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseRepository {

    override suspend fun apiFirebaseFirestore(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ): Boolean = withContext(Dispatchers.IO) {
        suspendCancellableCoroutineWithTimeout(
            TimeUnit.SECONDS.toMillis(5.toLong())
        ) { continuation ->
            firebaseFirestore.collection(COLLECTION_DATABASE)
                .document(DB_KEY)
                .collection(COLLECTION_PURCHASE)
                .document(purchaseCollectionId)
                .collection(PURCHASE)
                .document(purchaseModel.createId)
                .set(purchaseModel)
                .addOnSuccessListener {
                    if (continuation.isCancelled.not()) continuation.resume(true)
                }
                .addOnFailureListener {
                    if (continuation.isCancelled.not()) continuation.resumeWithException(it)
                }
        }
    }
}
