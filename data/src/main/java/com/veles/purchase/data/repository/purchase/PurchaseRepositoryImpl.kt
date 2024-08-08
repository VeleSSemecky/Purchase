package com.veles.purchase.data.repository.purchase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.veles.purchase.config.EnvironmentConfig.PURCHASE
import com.veles.purchase.data.core.extensions.snapshotFlow
import com.veles.purchase.data.core.extensions.toUnit
import com.veles.purchase.data.extensions.collectionPurchase
import com.veles.purchase.data.extensions.purchase
import com.veles.purchase.data.networking.entity.purchase.PurchaseDto
import com.veles.purchase.data.networking.entity.purchase.toPurchaseModel
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

@Singleton
class PurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : PurchaseRepository {

    override suspend fun getPurchase(
        collectionId: String,
        purchaseId: String
    ): PurchaseModel = firebaseFirestore.purchase(collectionId)
        .document(purchaseId).get().await()
        .toObject<PurchaseDto>()?.toPurchaseModel()
        ?: throw IllegalArgumentException("PurchaseModel is null")

    override suspend fun getSearchPurchaseList(
        collectionId: String,
        search: String
    ) = firebaseFirestore.purchase(collectionId)
        .whereGreaterThanOrEqualTo("text", search)
        .whereLessThanOrEqualTo("text", search + "\uF7FF").limit(40).get().await()
        .documents.mapNotNull { it.toObject<PurchaseDto>()?.toPurchaseModel() }

    override fun getPurchaseFlow(
        collectionId: String
    ): Flow<List<PurchaseModel>> = firebaseFirestore.purchase(collectionId)
        .snapshotFlow()
        .map { snapshot ->
            snapshot.documents.mapNotNull { it.toObject<PurchaseDto>()?.toPurchaseModel() }
        }

    override suspend fun deletePurchase(
        purchaseId: String,
        collectionId: String
    ) = suspendCancellableCoroutineWithTimeout {
        firebaseFirestore.collectionPurchase
            .document(collectionId)
            .collection(PURCHASE)
            .document(purchaseId)
            .delete()
            .await().toUnit()
    }

    override suspend fun setPurchase(
        purchaseModel: PurchaseModel,
        collectionId: String
    ) = suspendCancellableCoroutineWithTimeout {
        firebaseFirestore.collectionPurchase
            .document(collectionId)
            .collection(PURCHASE)
            .document(purchaseModel.createId)
            .set(purchaseModel)
            .await().toUnit()
    }
}
