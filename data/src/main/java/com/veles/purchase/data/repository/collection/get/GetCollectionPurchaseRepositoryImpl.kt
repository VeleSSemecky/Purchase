package com.veles.purchase.data.repository.collection.get

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.LIST_MEMBERS
import com.veles.purchase.data.core.extensions.snapshotFlow
import com.veles.purchase.data.extensions.collectionPurchase
import com.veles.purchase.data.networking.entity.purchase.PurchaseCollectionDto
import com.veles.purchase.data.networking.entity.purchase.toPurchaseCollectionModel
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.GetCollectionPurchaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCollectionPurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : GetCollectionPurchaseRepository {

    override suspend fun getListCollectionPurchases(): Flow<List<PurchaseCollectionModel>> {
        val user = firebaseAuth.currentUser
            ?: throw IllegalArgumentException("FirebaseAuth currentUser is null")
        return firebaseFirestore.collectionPurchase
            .whereArrayContains(LIST_MEMBERS, user.uid)
            .snapshotFlow()
            .map { snapshot ->
                snapshot.documents.mapNotNull {
                    it.toObject<PurchaseCollectionDto>()?.toPurchaseCollectionModel()
                }
            }
    }

    override suspend fun getCollectionPurchase(id: String): PurchaseCollectionModel =
        firebaseFirestore.collectionPurchase
            .document(id).get().await().run {
                toObject<PurchaseCollectionDto>()?.toPurchaseCollectionModel()
            }
            ?: throw IllegalArgumentException("PurchaseCollectionModel is null")
}
