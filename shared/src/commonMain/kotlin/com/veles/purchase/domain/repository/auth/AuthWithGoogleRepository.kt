package com.veles.purchase.domain.repository.auth

interface AuthWithGoogleRepository {

    suspend fun firebaseAuthWithGoogle(resultIdTokenAndAccessToken: Pair<String, String?>)
}
