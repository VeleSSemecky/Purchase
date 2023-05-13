package com.veles.purchase.domain.usecase.user

import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.user.FirebaseGetUserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserUseCase @Inject constructor(
    private val firebaseGetUserRepository: FirebaseGetUserRepository
) {

    suspend fun getUserPurchase(): UserPurchaseModel? =
        firebaseGetUserRepository.apiGetUserPurchase()

    fun isNeedLogin(): Boolean = firebaseGetUserRepository.isNeedLogin()

    suspend operator fun invoke(): Flow<List<UserPurchaseModel>> =
        firebaseGetUserRepository.apiFirebaseFirestore()
}
