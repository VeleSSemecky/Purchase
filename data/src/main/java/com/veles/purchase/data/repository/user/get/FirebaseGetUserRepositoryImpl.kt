package com.veles.purchase.data.repository.user.get

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.UID
import com.veles.purchase.config.EnvironmentConfig.USER_PURCHASE
import com.veles.purchase.data.core.extensions.snapshotFlow
import com.veles.purchase.data.model.user.UserPurchaseModelData
import com.veles.purchase.data.model.user.toUserPurchaseModel
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.user.FirebaseGetUserRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

@Singleton
class FirebaseGetUserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : FirebaseGetUserRepository {

    override suspend fun apiFirebaseFirestore(): Flow<List<UserPurchaseModel>> = firebaseFirestore
        .collection(COLLECTION_DATABASE)
        .document(EnvironmentConfig.DB_KEY)
        .collection(USER_PURCHASE)
        .snapshotFlow()
        .map { snapshot ->
            snapshot.documents.mapNotNull {
                it.toObject<UserPurchaseModelData>()?.toUserPurchaseModel()
            }
        }.map { value ->
            value.toMutableList()
                .filterNot { userPurchase -> userPurchase.uid == firebaseAuth.currentUser?.uid }
        }

    override suspend fun apiGetUserPurchase(): UserPurchaseModel? =
        suspendCancellableCoroutineWithTimeout {
            firebaseFirestore
                .collection(COLLECTION_DATABASE)
                .document(EnvironmentConfig.DB_KEY)
                .collection(USER_PURCHASE)
                .whereEqualTo(UID, firebaseAuth.currentUser?.uid).get()
                .await()
                .documents.firstNotNullOfOrNull {
                    it.toObject<UserPurchaseModelData>()?.toUserPurchaseModel()
                }
        }

    override fun isNeedLogin(): Boolean =
        firebaseAuth.currentUser == null && firebaseAuth.uid == null
}
