package com.veles.purchase.presentation.di.module

import com.veles.purchase.presentation.data.notification.fcm.FirebaseMessagingService
import org.koin.dsl.module

/**
 * Koin module for notification dependencies
 * Converted from Dagger NotificationModule
 */
val notificationModule = module {

    single { FirebaseMessagingService() }
}
