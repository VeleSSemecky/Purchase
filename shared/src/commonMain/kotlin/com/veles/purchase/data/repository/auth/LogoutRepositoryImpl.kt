package com.veles.purchase.data.repository.auth

import com.veles.purchase.domain.repository.auth.LogoutRepository
import dev.gitlive.firebase.auth.FirebaseAuth

/**
 * KMP implementation of LogoutRepository using GitLive Firebase Auth.
 * Platform-specific credential clearing (e.g. Android CredentialManager)
 * is handled via platform modules if needed.
 */
class LogoutRepositoryImpl(private val auth: FirebaseAuth) : LogoutRepository {

    override suspend fun logout() {
        auth.signOut()
    }
}
