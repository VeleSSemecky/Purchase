package com.veles.purchase.data.repository.purchase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_PURCHASE
import com.veles.purchase.data.model.purchase.PurchaseModelData
import com.veles.purchase.data.model.purchase.toPurchaseModel
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Singleton
class PurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : PurchaseRepository {

    override fun getFlowPurchase(
        purchaseCollectionId: String,
        search: String
    ) = flow {
        when {
            search.isEmpty() -> emitAll(apiFirebaseFirestorePurchase(purchaseCollectionId))
            else -> emit(searchPurchase(purchaseCollectionId, search))
        }
    }

    override suspend fun getPurchase(
        collectionPurchaseId: String,
        purchaseId: String
    ): PurchaseModel = firebaseFirestore
        .collection(COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(COLLECTION_PURCHASE)
        .document(collectionPurchaseId)
        .collection(EnvironmentConfig.PURCHASE)
        .document(purchaseId).get().await()
        .toObject<PurchaseModelData>()?.toPurchaseModel()
        ?: throw IllegalArgumentException("PurchaseModel is null")

    private suspend fun searchPurchase(
        purchaseCollectionId: String,
        search: String
    ) = firebaseFirestore
        .collection(COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(COLLECTION_PURCHASE)
        .document(purchaseCollectionId)
        .collection(EnvironmentConfig.PURCHASE)
        .whereGreaterThanOrEqualTo("text", search)
        .whereLessThanOrEqualTo("text", search + "\uF7FF").limit(40).get().await()
        .documents.mapNotNull { it.toObject<PurchaseModelData>()?.toPurchaseModel() }

    private suspend fun apiFirebaseFirestorePurchase(
        modelCollectionPurchaseId: String
    ): Flow<List<PurchaseModel>> = callbackFlow {
        val databaseReference =
            firebaseFirestore
                .collection(COLLECTION_DATABASE)
                .document(EnvironmentConfig.DB_KEY)
                .collection(COLLECTION_PURCHASE)
                .document(modelCollectionPurchaseId)
                .collection("purchase")
                .addSnapshotListener { snapshot, e ->
                    when (snapshot != null && e == null) {
                        true -> trySendBlocking(
                            snapshot.documents.mapNotNull {
                                it.toObject<PurchaseModelData>()?.toPurchaseModel()
                            }
                        )
                        false -> close(e)
                    }
                }
        awaitClose {
            databaseReference.remove()
            close()
        }
    }
}
