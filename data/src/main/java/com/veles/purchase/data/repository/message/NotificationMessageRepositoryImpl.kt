package com.veles.purchase.data.repository.message

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.veles.purchase.config.EnvironmentConfig.UID
import com.veles.purchase.data.extensions.collectionPurchase
import com.veles.purchase.data.extensions.userPurchase
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.data.networking.entity.fcm.DataModelData
import com.veles.purchase.data.networking.entity.fcm.NotificationMessageModelData
import com.veles.purchase.data.networking.entity.purchase.PurchaseCollectionDto
import com.veles.purchase.data.networking.entity.user.UserDto
import com.veles.purchase.data.networking.service.message.NotificationMessageService
import com.veles.purchase.domain.repository.message.NotificationMessageRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class NotificationMessageRepositoryImpl @Inject constructor(
    private val notificationMessageService: NotificationMessageService,
    private val firebaseFirestore: FirebaseFirestore,
    private val dataStore: DataStore
) : NotificationMessageRepository {

    override suspend fun sendNotificationMessage(
        purchaseCollectionId: String,
        message: String
    ) {
        val listMembers = firebaseFirestore.collectionPurchase
            .document(purchaseCollectionId).get().await()
            .toObject<PurchaseCollectionDto>()?.listMembers
            ?: return

        if (listMembers.isEmpty()) return

        val listUsersMessageToken = firebaseFirestore.userPurchase
            .whereIn(UID, listMembers)
            .get().await().documents.mapNotNull {
                it?.toObject<UserDto>()?.fcmToken
            }

        if (listUsersMessageToken.isEmpty()) return

        val notificationMessageModel = NotificationMessageModelData(
            data = DataModelData(
                "New purchase",
                message,
                dataStore.getFCMToken()
            ),
            registrationIds = listUsersMessageToken
        )

//        notificationMessageService.sendNotificationMessage(notificationMessageModel)
    }
}
