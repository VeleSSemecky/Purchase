package com.veles.purchase.presentation.data.notification.fcm

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.RemoteMessage
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.domain.repository.user.FirebaseMessageTokenRepository
import com.veles.purchase.presentation.base.mvvm.service.BaseFirebaseMessagingService
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.android.ext.android.inject

class FirebaseMessagingService : BaseFirebaseMessagingService() {

    private val dataStore: DataStore by inject()
    private val firebaseAuth: FirebaseAuth by inject()
    private val firebaseMessageNotification: FirebaseMessageNotification by inject()
    private val firebaseMessageTokenRepository: FirebaseMessageTokenRepository by inject()

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
