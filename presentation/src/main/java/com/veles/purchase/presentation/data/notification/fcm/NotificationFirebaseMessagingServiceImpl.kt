package com.veles.purchase.presentation.data.notification.fcm

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.domain.model.fcm.DataModel
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.repository.user.token.FirebaseSetFcmTokenUserRepository
import dagger.android.AndroidInjection
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotificationFirebaseMessagingServiceImpl :
    FirebaseMessagingService(),
    NotificationFirebaseMessagingService {

    @Inject
    lateinit var dataStore: DataStore

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var firebaseSetFcmTokenUserRepository: FirebaseSetFcmTokenUserRepository

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        logger.i("onMessageReceived", "data.values  ${remoteMessage.data.values}")
        logger.i("onMessageReceived", "data ${remoteMessage.data}")
        logger.i("onMessageReceived", "notification ${remoteMessage.notification}")
        val dataModel = gson.fromJson(gson.toJson(remoteMessage.data), DataModel::class.java)
        if (dataModel.tokenSender != dataStore.getFCMToken() && dataStore.isInForeground().not()) {
            NotificationBean.sendNotification(applicationContext, dataModel)
        }
    }

    override fun onDestroy() {
        serviceJob.cancel()
        super.onDestroy()
    }

    private fun FirebaseUser.createUserPurchase(fcmToken: String): UserPurchaseModel =
        UserPurchaseModel(
            uid,
            providerId,
            displayName,
            email,
            phoneNumber,
            fcmToken,
            photoUrl?.toString()
        )

    override fun onNewToken(fcmToken: String) {
        super.onNewToken(fcmToken)
        dataStore.setFCMToken(fcmToken)
        serviceScope.launch(handler) {
            firebaseAuth.currentUser?.let {
                val user = it.createUserPurchase(fcmToken)
                firebaseSetFcmTokenUserRepository.apiFirebaseFirestore(user)
            }
        }
        logger.i("NotificationFirebase", "onNewToken: $fcmToken")
    }
}
