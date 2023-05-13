package com.veles.purchase.data.repository.purchase.set

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_PURCHASE
import com.veles.purchase.config.EnvironmentConfig.DB_KEY
import com.veles.purchase.config.EnvironmentConfig.PURCHASE
import com.veles.purchase.data.core.extensions.toUnit
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.SetPurchaseRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Singleton
class SetPurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : SetPurchaseRepository {

    private fun FirebaseFirestore.getCollectionPurchase() =
        collection(COLLECTION_DATABASE)
            .document(DB_KEY)
            .collection(COLLECTION_PURCHASE)

    override suspend fun setPurchase(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) = withContext(Dispatchers.IO) {
        suspendCancellableCoroutineWithTimeout(TimeUnit.SECONDS.toMillis(5.toLong())) {
            firebaseFirestore.getCollectionPurchase()
                .document(purchaseCollectionId)
                .collection(PURCHASE)
                .document(purchaseModel.createId)
                .set(purchaseModel)
                .await().toUnit()
        }
    }
}
