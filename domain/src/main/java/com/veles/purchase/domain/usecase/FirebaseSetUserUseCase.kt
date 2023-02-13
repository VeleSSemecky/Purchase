package com.veles.purchase.domain.usecase

import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import com.veles.purchase.domain.repository.user.set.FirebaseSetUserRepository
import javax.inject.Inject

class FirebaseSetUserUseCase @Inject constructor(
    private val firebaseSetUserRepository: FirebaseSetUserRepository,
    private val authWithGoogleRepository: AuthWithGoogleRepository
) {

    suspend fun apiFirebaseFirestore(userInfo: UserPurchaseModel): Boolean =
        firebaseSetUserRepository.apiFirebaseFirestore(userInfo)

    suspend operator fun invoke(idToken: String): Boolean =
        authWithGoogleRepository.firebaseAuthWithGoogle(idToken)
}
