package com.veles.purchase.data.repository.purchase

import com.veles.purchase.config.EnvironmentConfig.PURCHASE
import com.veles.purchase.data.entity.purchase.PurchaseDto
import com.veles.purchase.data.entity.purchase.toPurchaseDto
import com.veles.purchase.data.entity.purchase.toPurchaseModel
import com.veles.purchase.data.extensions.collectionPurchase
import com.veles.purchase.data.extensions.purchase
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Firebase KMP implementation of PurchaseRepository
 * Handles purchase CRUD operations in Firestore
 */
class PurchaseRepositoryImpl(private val firestore: FirebaseFirestore) : PurchaseRepository {

    override suspend fun getPurchase(
        collectionId: String,
        purchaseId: String
    ): PurchaseModel {
        val snapshot = firestore.purchase(collectionId)
            .document(purchaseId)
            .get()

        return snapshot.data<PurchaseDto>().toPurchaseModel()
    }

    override suspend fun getSearchPurchaseList(
        collectionId: String,
        search: String
    ): List<PurchaseModel> {
        val snapshot = firestore.purchase(collectionId)
            .where {
                "text" greaterThanOrEqualTo search
                "text" lessThanOrEqualTo search + "\uF7FF"
            }
            .limit(40)
            .get()

        return snapshot.documents.mapNotNull {
            it.data<PurchaseDto>().toPurchaseModel()
        }
    }

    override fun getPurchaseFlow(
        collectionId: String
    ): Flow<List<PurchaseModel>> = firestore.purchase(collectionId)
        .snapshots
        .map { snapshot ->
            snapshot.documents.mapNotNull {
                it.data<PurchaseDto>().toPurchaseModel()
            }
        }

    override suspend fun deletePurchase(
        purchaseId: String,
        collectionId: String
    ) {
        firestore.collectionPurchase
            .document(collectionId)
            .collection(PURCHASE)
            .document(purchaseId)
            .delete()
    }

    override suspend fun setPurchase(
        purchaseModel: PurchaseModel,
        collectionId: String
    ) {
        firestore.collectionPurchase
            .document(collectionId)
            .collection(PURCHASE)
            .document(purchaseModel.createId)
            .set(purchaseModel.toPurchaseDto(), merge = true)
    }
}
