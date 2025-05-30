package com.veles.purchase.data.repository.collection.category

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.veles.purchase.data.extensions.collectionPurchase
import com.veles.purchase.data.networking.entity.purchase.PurchaseCollectionDto
import com.veles.purchase.data.networking.entity.purchase.toPurchaseCollectionModel
import com.veles.purchase.data.networking.entity.purchase.toPurchaseCollectionModelDto
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.purchase.PurchaseCategoryRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.tasks.await

@Singleton
class PurchaseCategoryRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : PurchaseCategoryRepository {

    override suspend fun setPurchaseCategory(
        purchaseCollection: PurchaseCollectionModel
    ): Unit = suspendCancellableCoroutineWithTimeout {

        val purchaseCollectionModelDto = purchaseCollection.toPurchaseCollectionModelDto()

        firebaseFirestore.collectionPurchase
            .document(purchaseCollectionModelDto.id)
            .set(purchaseCollectionModelDto)
            .await()
    }

    override fun getPurchaseCategory(
        purchaseCollectionId: String
    ): Flow<List<PurchaseCategoryModel>> = firebaseFirestore.collectionPurchase
        .document(purchaseCollectionId).snapshots()
        .mapNotNull { snapshot ->
            snapshot.toObject<PurchaseCollectionDto>()?.toPurchaseCollectionModel()?.categoryModels ?: emptyList()
        }
}
