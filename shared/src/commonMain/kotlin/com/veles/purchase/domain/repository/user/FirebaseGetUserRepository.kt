package com.veles.purchase.domain.repository.user

import com.veles.purchase.domain.model.user.UserPurchaseModel
import kotlinx.coroutines.flow.Flow

interface FirebaseGetUserRepository {

    suspend fun apiFirebaseFirestore(): Flow<List<UserPurchaseModel>>

    suspend fun apiGetUserPurchase(): UserPurchaseModel?

    fun isNeedLogin(): Boolean
}
