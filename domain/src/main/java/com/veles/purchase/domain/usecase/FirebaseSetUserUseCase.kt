package com.veles.purchase.domain.usecase

import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import javax.inject.Inject

class FirebaseSetUserUseCase @Inject constructor(
    private val authWithGoogleRepository: AuthWithGoogleRepository
) {

    suspend operator fun invoke(idToken: String) =
        authWithGoogleRepository.firebaseAuthWithGoogle(idToken)
}
