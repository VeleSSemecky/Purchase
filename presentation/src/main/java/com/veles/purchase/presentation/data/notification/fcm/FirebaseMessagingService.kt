package com.veles.purchase.presentation.data.notification.fcm

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.RemoteMessage
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.domain.repository.user.token.FirebaseMessageTokenRepository
import com.veles.purchase.presentation.base.mvvm.service.BaseFirebaseMessagingService
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler

class FirebaseMessagingService : BaseFirebaseMessagingService() {

    @Inject
    lateinit var dataStore: DataStore

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseMessageNotification: FirebaseMessageNotification

    @Inject
    lateinit var firebaseMessageTokenRepository: FirebaseMessageTokenRepository

    private val handler = CoroutineExceptionHandler { _, exception ->
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        firebaseMessageNotification(remoteMessage.data)
    }

    override fun onNewToken(token: String) {
        dataStore.setFCMToken(token)
        setNewToken(token)
    }

    private fun setNewToken(token: String) = launch(handler) {
        val uid = firebaseAuth.currentUser?.uid ?: return@launch
        firebaseMessageTokenRepository.sendMessageToken(uid, token)
    }
}
