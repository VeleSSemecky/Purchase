package com.veles.purchase.domain.repository.user

interface FirebaseMessageTokenRepository {

    suspend fun sendMessageToken(userUid: String, messageToken: String): Void
}
