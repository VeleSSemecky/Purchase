package com.veles.purchase.domain.usecase.auth

import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import com.veles.purchase.domain.repository.auth.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authWithGoogleRepository: AuthWithGoogleRepository,
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke() {
        val idToken = loginRepository.getGoogleIdToken()
        authWithGoogleRepository.firebaseAuthWithGoogle(idToken)
    }
}
