package com.veles.purchase.data.repository.collection.category

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.data.extensions.collectionPurchase
import com.veles.purchase.data.networking.entity.purchase.toPurchaseCollectionModelDto
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.purchase.PurchaseCategoryRepository
import javax.inject.Inject
import javax.inject.Singleton
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
}
