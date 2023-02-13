package com.veles.purchase.data.repository.user.get

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.UID
import com.veles.purchase.config.EnvironmentConfig.USER_PURCHASE
import com.veles.purchase.data.model.user.UserPurchaseModelData
import com.veles.purchase.data.model.user.toUserPurchaseModel
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.user.get.FirebaseGetUserRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

@Singleton
class FirebaseGetUserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : FirebaseGetUserRepository {

    override suspend fun apiFirebaseFirestore(): Flow<List<UserPurchaseModel>> {
        return callbackFlow {
            val databaseReference =
                firebaseFirestore
                    .collection(COLLECTION_DATABASE)
                    .document(EnvironmentConfig.DB_KEY)
                    .collection(USER_PURCHASE)
                    .addSnapshotListener { snapshot, e ->
                        when (snapshot != null && e == null) {
                            true -> trySendBlocking(
                                snapshot.documents.mapNotNull {
                                    it.toObject<UserPurchaseModelData>()?.toUserPurchaseModel()
                                }
                            )
                            false -> close(e)
                        }
                    }
            awaitClose {
                databaseReference.remove()
                close()
            }
        }.map { value ->
            value.toMutableList()
                .filterNot { userPurchase -> userPurchase.uid == firebaseAuth.currentUser?.uid }
        }
    }

    override suspend fun apiGetUserPurchase(): UserPurchaseModel? =
        suspendCancellableCoroutineWithTimeout(TimeUnit.SECONDS.toMillis(5.toLong())) { continuation ->
            firebaseFirestore
                .collection(COLLECTION_DATABASE)
                .document(EnvironmentConfig.DB_KEY)
                .collection(USER_PURCHASE)
                .whereEqualTo(UID, firebaseAuth.currentUser?.uid).get()
                .addOnSuccessListener { snapshot ->
                    val userPurchaseModel: UserPurchaseModel? =
                        snapshot.documents.firstNotNullOfOrNull {
                            it.toObject<UserPurchaseModelData>()?.toUserPurchaseModel()
                        }
                    continuation.resume(userPurchaseModel)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override fun isNeedLogin(): Boolean =
        firebaseAuth.currentUser == null && firebaseAuth.uid == null
}
