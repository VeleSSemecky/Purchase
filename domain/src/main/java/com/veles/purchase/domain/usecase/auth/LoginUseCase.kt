package com.veles.purchase.domain.usecase.auth

import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authWithGoogleRepository: AuthWithGoogleRepository
) {

    suspend operator fun invoke(idToken: String) {
        authWithGoogleRepository.firebaseAuthWithGoogle(idToken)
    }
}
