package com.veles.purchase.presentation

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.veles.purchase.presentation.base.AppLifecycleObserver
import com.veles.purchase.presentation.di.module.allPresentationKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.android.ext.android.inject

class AppApplication : Application() {

    private val appLifecycleObserver: AppLifecycleObserver by inject()

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin with converted modules
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AppApplication)
            modules(allPresentationKoinModules) // Використовуємо перетворені модулі
        }

        // Initialize lifecycle observer
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }
}
