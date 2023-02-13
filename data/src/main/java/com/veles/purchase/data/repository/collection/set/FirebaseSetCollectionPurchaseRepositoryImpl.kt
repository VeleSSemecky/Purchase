package com.veles.purchase.data.repository.collection.set

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_PURCHASE
import com.veles.purchase.data.model.user.createUserPurchase
import com.veles.purchase.data.model.user.toUserPurchaseModel
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.set.FirebaseSetCollectionPurchaseRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class FirebaseSetCollectionPurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : FirebaseSetCollectionPurchaseRepository {

    override suspend fun apiFirebaseFirestore(
        purchaseCollection: PurchaseCollectionModel
    ): Boolean =
        suspendCancellableCoroutineWithTimeout(TimeUnit.SECONDS.toMillis(5.toLong())) { continuation ->
            val user = firebaseAuth.currentUser

            if (user == null) {
                continuation.resume(false)
                return@suspendCancellableCoroutineWithTimeout
            }

            val changePurchaseCollection = purchaseCollection.copy(
                creator = user.createUserPurchase().toUserPurchaseModel(),
                listMembers = purchaseCollection.listMembers.toMutableList().apply {
                    add(user.uid)
                }
            )

            firebaseFirestore.collection(COLLECTION_DATABASE)
                .document(EnvironmentConfig.DB_KEY)
                .collection(COLLECTION_PURCHASE)
                .document(changePurchaseCollection.id)
                .set(changePurchaseCollection)
                .addOnCompleteListener {
                    if (continuation.isCancelled.not()) continuation.resume(it.isSuccessful)
                }
                .addOnFailureListener {
                    if (continuation.isCancelled.not()) continuation.resumeWithException(it)
                }
        }
}
