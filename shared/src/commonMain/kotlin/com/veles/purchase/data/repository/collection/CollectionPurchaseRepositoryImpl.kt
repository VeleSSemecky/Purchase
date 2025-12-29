package com.veles.purchase.data.repository.collection

import com.veles.purchase.data.entity.collection.toPurchaseCollectionDto
import com.veles.purchase.data.extensions.collectionPurchase
import com.veles.purchase.data.extensions.toUserDto
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.CollectionPurchaseRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore

/**
 * Firebase KMP implementation of CollectionPurchaseRepository
 * Handles collection CRUD operations in Firestore
 */
class CollectionPurchaseRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : CollectionPurchaseRepository {

    override suspend fun setCollectionPurchase(
        purchaseCollection: PurchaseCollectionModel
    ) {
        val user = auth.currentUser
            ?: throw IllegalArgumentException("FirebaseAuth currentUser is null")

        val updatedCollection = purchaseCollection.copy(
            creator = user.toUserDto().let { dto ->
                com.veles.purchase.domain.model.user.UserPurchaseModel(
                    uid = dto.uid,
                    providerId = dto.providerId,
                    displayName = dto.displayName,
                    email = dto.email,
                    phoneNumber = dto.phoneNumber,
                    fcmToken = dto.fcmToken,
                    photoUrl = dto.photoUrl
                )
            },
            listMembers = purchaseCollection.listMembers.toMutableList().apply {
                if (!contains(user.uid)) {
                    add(user.uid)
                }
            }
        )

        firestore.collectionPurchase
            .document(updatedCollection.id)
            .set(updatedCollection.toPurchaseCollectionDto())
    }
}

