package com.veles.purchase.presentation

import androidx.lifecycle.ProcessLifecycleOwner
import com.veles.purchase.presentation.base.AppLifecycleObserver
import com.veles.purchase.presentation.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class AppApplication : DaggerApplication() {

    @Inject
    lateinit var appLifecycleObserver: AppLifecycleObserver

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().context(applicationContext).build()

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }
}
