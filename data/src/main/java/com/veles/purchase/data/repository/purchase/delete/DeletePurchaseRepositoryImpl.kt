package com.veles.purchase.data.repository.purchase.delete

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_PURCHASE
import com.veles.purchase.config.EnvironmentConfig.PURCHASE
import com.veles.purchase.data.core.extensions.toUnit
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.repository.storage.DeletePurchaseRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class DeletePurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : DeletePurchaseRepository {

    private fun FirebaseFirestore.getCollectionPurchase() =
        collection(COLLECTION_DATABASE)
            .document(EnvironmentConfig.DB_KEY)
            .collection(COLLECTION_PURCHASE)

    override suspend fun deletePurchase(
        purchaseCreateId: String,
        purchaseCollectionId: String
    ) = suspendCancellableCoroutineWithTimeout(TimeUnit.SECONDS.toMillis(5.toLong())) {
        firebaseFirestore.getCollectionPurchase()
            .document(purchaseCollectionId)
            .collection(PURCHASE)
            .document(purchaseCreateId)
            .delete()
            .await().toUnit()
    }
}
