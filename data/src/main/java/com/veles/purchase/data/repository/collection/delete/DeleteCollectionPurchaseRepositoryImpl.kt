package com.veles.purchase.data.repository.collection.delete

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.data.extensions.collectionPurchase
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

    override suspend fun deletePurchaseCollection(
        purchaseCollection: PurchaseCollectionModel
    ) {
        val user = firebaseAuth.currentUser ?: throw IllegalArgumentException("CurrentUser is null")
        val documentReference = firebaseFirestore.collectionPurchase.document(purchaseCollection.id)
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
