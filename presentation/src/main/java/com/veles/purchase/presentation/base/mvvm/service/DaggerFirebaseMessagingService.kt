package com.veles.purchase.presentation.base.mvvm.service

import com.google.firebase.messaging.FirebaseMessagingService

/**
 * Base Firebase Messaging Service converted from Dagger to Koin
 */
abstract class KoinFirebaseMessagingService : FirebaseMessagingService() {

    // Koin injection is handled automatically through context
    // No need for manual injection like in Dagger

    override fun onCreate() {
        super.onCreate()
    }

    abstract override fun onNewToken(token: String)
}
