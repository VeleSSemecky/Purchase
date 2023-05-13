package com.veles.purchase.data.repository.purchase.get

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_PURCHASE
import com.veles.purchase.data.core.extensions.snapshotFlow
import com.veles.purchase.data.model.purchase.PurchaseModelData
import com.veles.purchase.data.model.purchase.toPurchaseModel
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.GetPurchaseRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

@Singleton
class GetGetPurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : GetPurchaseRepository {

    private fun String.getCollectionPurchase() = firebaseFirestore
        .collection(COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(COLLECTION_PURCHASE)
        .document(this)
        .collection(EnvironmentConfig.PURCHASE)

    override fun getLisPurchases(
        collectionId: String,
        search: String
    ) = flow {
        when {
            search.isEmpty() -> emitAll(apiFirebaseFirestorePurchase(collectionId))
            else -> emit(searchPurchase(collectionId, search))
        }
    }

    override suspend fun getPurchase(
        collectionId: String,
        purchaseId: String
    ): PurchaseModel = collectionId.getCollectionPurchase()
        .document(purchaseId).get().await()
        .toObject<PurchaseModelData>()?.toPurchaseModel()
        ?: throw IllegalArgumentException("PurchaseModel is null")

    private suspend fun searchPurchase(
        collectionId: String,
        search: String
    ) = collectionId.getCollectionPurchase()
        .whereGreaterThanOrEqualTo("text", search)
        .whereLessThanOrEqualTo("text", search + "\uF7FF").limit(40).get().await()
        .documents.mapNotNull { it.toObject<PurchaseModelData>()?.toPurchaseModel() }

    private fun apiFirebaseFirestorePurchase(
        collectionId: String
    ): Flow<List<PurchaseModel>> =
        collectionId.getCollectionPurchase().snapshotFlow().map { snapshot ->
            snapshot.documents.mapNotNull { it.toObject<PurchaseModelData>()?.toPurchaseModel() }
        }
}
