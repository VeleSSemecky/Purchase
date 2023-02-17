package com.veles.purchase.domain.repository.user.token

interface FirebaseMessageTokenRepository {
    suspend fun sendMessageToken(userUid: String, messageToken: String): Void
}
