package com.veles.purchase.data.repository.collection.get

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_PURCHASE
import com.veles.purchase.config.EnvironmentConfig.LIST_MEMBERS
import com.veles.purchase.data.model.purchase.PurchaseCollectionModelData
import com.veles.purchase.data.model.purchase.toPurchaseCollectionModel
import com.veles.purchase.data.model.user.createUserPurchase
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.get.FirebaseCollectionPurchaseRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@Singleton
class FirebaseCollectionPurchaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val logger: Logger
) : FirebaseCollectionPurchaseRepository {

    override suspend fun apiFirebaseFirestorePurchase(): Flow<List<PurchaseCollectionModel>> {
        return callbackFlow {
            val user = firebaseAuth.currentUser
                ?: throw IllegalArgumentException("FirebaseAuth currentUser is null")
            val databaseReference =
                firebaseFirestore
                    .collection(COLLECTION_DATABASE)
                    .document(EnvironmentConfig.DB_KEY)
                    .collection(COLLECTION_PURCHASE)
                    .whereArrayContains(LIST_MEMBERS, user.uid)
                    .addSnapshotListener { snapshot, e ->
                        when (snapshot != null && e == null) {
                            true -> trySendBlocking(
                                snapshot.documents.mapNotNull {
                                    it.toObject<PurchaseCollectionModelData>()?.toPurchaseCollectionModel()
                                }
                            )
                            false -> close(e)
                        }
                    }
            awaitClose {
                databaseReference.remove()
                close()
            }
        }
    }

    override suspend fun apiFirebaseFirestoreDeletePurchaseCollection(
        purchaseCollection: PurchaseCollectionModel
    ): Boolean {
        return try {
            val user = firebaseAuth.currentUser ?: return false
            logger.i("apiFirebaseFirestorePurchase", user.createUserPurchase().toString())
            val documentReference = firebaseFirestore
                .collection(COLLECTION_DATABASE)
                .document(EnvironmentConfig.DB_KEY)
                .collection(COLLECTION_PURCHASE)
                .document(purchaseCollection.id)
            if (purchaseCollection.listMembers.size == 1) {
                documentReference.delete().await()
            } else {
                documentReference.set(
                    purchaseCollection.copy(
                        listMembers = ArrayList(
                            purchaseCollection
                                .listMembers
                                .filterNot { it == user.uid }
                        )
                    )
                ).await()
            }
            true
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            false
        }
    }

    override suspend fun apiFirebaseFirestorePurchase(
        id: String
    ): PurchaseCollectionModel = firebaseFirestore
        .collection(COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(COLLECTION_PURCHASE)
        .document(id).get().await().toObject<PurchaseCollectionModelData>()?.toPurchaseCollectionModel()
        ?: throw IllegalArgumentException("PurchaseCollectionModel is null")
}
