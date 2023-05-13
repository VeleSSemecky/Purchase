package com.veles.purchase.data.repository.collection.get

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_PURCHASE
import com.veles.purchase.config.EnvironmentConfig.LIST_MEMBERS
import com.veles.purchase.data.core.extensions.snapshotFlow
import com.veles.purchase.data.model.purchase.PurchaseCollectionModelData
import com.veles.purchase.data.model.purchase.toPurchaseCollectionModel
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.GetCollectionPurchaseRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

@Singleton
class GetCollectionPurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : GetCollectionPurchaseRepository {

    private fun FirebaseFirestore.getCollectionPurchase() = collection(COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(COLLECTION_PURCHASE)

    override suspend fun getListCollectionPurchases(): Flow<List<PurchaseCollectionModel>> {
        val user = firebaseAuth.currentUser
            ?: throw IllegalArgumentException("FirebaseAuth currentUser is null")
        return firebaseFirestore.getCollectionPurchase()
            .whereArrayContains(LIST_MEMBERS, user.uid)
            .snapshotFlow()
            .map { snapshot ->
                snapshot.documents.mapNotNull {
                    it.toObject<PurchaseCollectionModelData>()?.toPurchaseCollectionModel()
                }
            }
    }

    override suspend fun getCollectionPurchase(id: String): PurchaseCollectionModel =
        firebaseFirestore.getCollectionPurchase()
            .document(id).get().await().toObject<PurchaseCollectionModelData>()
            ?.toPurchaseCollectionModel()
            ?: throw IllegalArgumentException("PurchaseCollectionModel is null")
}
