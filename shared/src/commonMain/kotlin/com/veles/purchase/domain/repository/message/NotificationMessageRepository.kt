package com.veles.purchase.domain.repository.message

interface NotificationMessageRepository {

    suspend fun sendNotificationMessage(purchaseCollectionId: String, message: String)
}
