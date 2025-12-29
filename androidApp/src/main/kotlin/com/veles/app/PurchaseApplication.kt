package com.veles.app

import android.app.Application
import com.veles.purchase.data.firebase.FirebaseInitializer
import com.veles.purchase.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class for Purchase KMP app
 *
 * Initializes Koin DI with all modules from shared
 */
class PurchaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseInitializer().apply {
            initialize(this@PurchaseApplication)
        }
        // Initialize Koin
        startKoin {
            androidContext(this@PurchaseApplication)
            modules(appModules)
        }
    }
}

