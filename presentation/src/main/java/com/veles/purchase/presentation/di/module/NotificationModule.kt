package com.veles.purchase.presentation.di.module

import com.veles.purchase.presentation.data.notification.fcm.NotificationFirebaseMessagingServiceImpl
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface NotificationModule {

    @ContributesAndroidInjector
    fun notificationFirebaseMessagingService(): NotificationFirebaseMessagingServiceImpl
}
