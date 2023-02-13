package com.veles.purchase.data.repository.message

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.config.EnvironmentConfig.COLLECTION_DATABASE
import com.veles.purchase.config.EnvironmentConfig.UID
import com.veles.purchase.config.EnvironmentConfig.USER_PURCHASE
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.data.model.fcm.DataModelData
import com.veles.purchase.data.model.fcm.NotificationMessageModelData
import com.veles.purchase.data.model.purchase.PurchaseCollectionModelData
import com.veles.purchase.data.model.user.UserPurchaseModelData
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
        val listMembers = firebaseFirestore
            .collection(COLLECTION_DATABASE)
            .document(EnvironmentConfig.DB_KEY)
            .collection(EnvironmentConfig.COLLECTION_PURCHASE)
            .document(purchaseCollectionId).get().await()
            .toObject<PurchaseCollectionModelData>()?.listMembers
            ?: return

        if (listMembers.isEmpty()) return

        val listUsersMessageToken = firebaseFirestore.collection(COLLECTION_DATABASE)
            .document(EnvironmentConfig.DB_KEY)
            .collection(USER_PURCHASE)
            .whereIn(UID, listMembers)
            .get().await().documents.mapNotNull {
                it?.toObject<UserPurchaseModelData>()?.fcmToken
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

        notificationMessageService.sendNotificationMessage(notificationMessageModel)
    }
}
