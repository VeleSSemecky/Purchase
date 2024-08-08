package com.veles.purchase.data.repository.collection.set

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.data.core.extensions.toUnit
import com.veles.purchase.data.extensions.collectionPurchase
import com.veles.purchase.data.networking.entity.user.toUserDto
import com.veles.purchase.data.networking.entity.user.toUserPurchaseModel
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.SetCollectionPurchaseRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class SetCollectionPurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : SetCollectionPurchaseRepository {

    override suspend fun setCollectionPurchase(
        purchaseCollection: PurchaseCollectionModel
    ) = suspendCancellableCoroutineWithTimeout {
        val user = firebaseAuth.currentUser
            ?: throw IllegalArgumentException("FirebaseAuth currentUser is null")

        val changePurchaseCollection = purchaseCollection.copy(
            creator = user.toUserDto().toUserPurchaseModel(),
            listMembers = purchaseCollection.listMembers.toMutableList().apply {
                add(user.uid)
            }
        )

        firebaseFirestore.collectionPurchase
            .document(changePurchaseCollection.id)
            .set(changePurchaseCollection)
            .await().toUnit()
    }
}
