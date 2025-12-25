package com.veles.purchase.data.repository.user

import com.veles.purchase.config.EnvironmentConfig.UID
import com.veles.purchase.data.entity.user.UserDto
import com.veles.purchase.data.entity.user.toUserPurchaseModel
import com.veles.purchase.data.extensions.userPurchase
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.user.FirebaseGetUserRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Firebase KMP implementation of FirebaseGetUserRepository
 * Uses GitLive Firebase KMP SDK for cross-platform support
 */
class FirebaseGetUserRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : FirebaseGetUserRepository {

    override suspend fun apiFirebaseFirestore(): Flow<List<UserPurchaseModel>> {
        return firestore.userPurchase
            .snapshots
            .map { snapshot ->
                snapshot.documents.mapNotNull { document ->
                    document.data<UserDto>().toUserPurchaseModel()
                }
            }
            .map { users ->
                // Filter out current user from the list
                users.filterNot { it.uid == auth.currentUser?.uid }
            }
    }

    override suspend fun apiGetUserPurchase(): UserPurchaseModel? {
        val currentUserId = auth.currentUser?.uid ?: return null

        val snapshot = firestore.userPurchase
            .where {
                UID equalTo currentUserId
            }
            .get()

        return snapshot.documents.firstOrNull()?.data<UserDto>()?.toUserPurchaseModel()
    }

    override fun isNeedLogin(): Boolean {
        return auth.currentUser == null
    }
}

