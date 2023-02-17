package com.veles.purchase.presentation.base.mvvm.service

import com.google.firebase.messaging.FirebaseMessagingService
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class DaggerFirebaseMessagingService : FirebaseMessagingService(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    abstract override fun onNewToken(token: String)

    override fun androidInjector(): AndroidInjector<Any>? = androidInjector
}
