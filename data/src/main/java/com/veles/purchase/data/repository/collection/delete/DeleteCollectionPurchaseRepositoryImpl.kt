package com.veles.purchase.data.repository.collection.delete

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.data.model.user.createUserPurchase
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.DeleteCollectionPurchaseRepository
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class DeleteCollectionPurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val logger: Logger
) : DeleteCollectionPurchaseRepository {

    private fun FirebaseFirestore.getCollectionPurchase() =
        collection(EnvironmentConfig.COLLECTION_DATABASE)
            .document(EnvironmentConfig.DB_KEY)
            .collection(EnvironmentConfig.COLLECTION_PURCHASE)

    override suspend fun deletePurchaseCollection(
        purchaseCollection: PurchaseCollectionModel
    ) {
        val user = firebaseAuth.currentUser ?: throw IllegalArgumentException("CurrentUser is null")
        logger.i("apiFirebaseFirestorePurchase", user.createUserPurchase().toString())
        val documentReference = firebaseFirestore.getCollectionPurchase()
            .document(purchaseCollection.id)
        val task = when (purchaseCollection.listMembers.size) {
            1 -> documentReference.delete()
            else -> documentReference.set(
                purchaseCollection.copy(
                    listMembers = purchaseCollection.listMembers.filterNot { it == user.uid }
                )
            )
        }
        task.await()
    }
}
