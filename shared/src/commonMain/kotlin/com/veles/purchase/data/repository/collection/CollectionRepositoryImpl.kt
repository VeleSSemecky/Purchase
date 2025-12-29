package com.veles.purchase.data.repository.collection

import com.veles.purchase.data.entity.collection.toPurchaseCollectionModel
import com.veles.purchase.data.extensions.collectionPurchase
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.CollectionPurchaseRepository
import com.veles.purchase.domain.repository.collection.CollectionRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of CollectionRepository using Firebase
 * Uses Firestore for collection management with real-time updates
 */
class CollectionRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val collectionPurchaseRepository: CollectionPurchaseRepository
) : CollectionRepository {

    override fun getCollections(): Flow<List<PurchaseCollectionModel>> {
        val userId = auth.currentUser?.uid
            ?: throw IllegalArgumentException("User not authenticated")

        return firestore.collectionPurchase
            .where { "listMembers" contains userId }
            .snapshots
            .map { snapshot ->
                snapshot.documents.map { doc ->
                    doc.data(com.veles.purchase.data.entity.collection.PurchaseCollectionDto.serializer())
                        .toPurchaseCollectionModel()
                }
            }
    }

    override suspend fun getCollection(collectionId: String): PurchaseCollectionModel? =
        try {
            val doc = firestore.collectionPurchase
                .document(collectionId)
                .get()

            doc.data(com.veles.purchase.data.entity.collection.PurchaseCollectionDto.serializer())
                .toPurchaseCollectionModel()
        } catch (_: Exception) {
            null // Return null if collection not found
        }

    override suspend fun saveCollection(collection: PurchaseCollectionModel) {
        collectionPurchaseRepository.setCollectionPurchase(collection)
    }

    override suspend fun deleteCollection(collection: PurchaseCollectionModel) {
        firestore.collectionPurchase
            .document(collection.id)
            .delete()
    }
}

