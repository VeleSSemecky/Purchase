package com.veles.purchase.domain.usecase

import com.veles.purchase.domain.repository.message.NotificationMessageRepository
import javax.inject.Inject

class NotificationMessageUseCase @Inject constructor(
    private val notificationMessageRepository: NotificationMessageRepository
) {

    suspend fun apiNotificationMessage(
        purchaseCollectionId: String,
        message: String
    ) = notificationMessageRepository.sendNotificationMessage(
        purchaseCollectionId,
        message
    )
}
