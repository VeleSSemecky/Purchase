package com.veles.app

import android.app.Application
import com.veles.purchase.config.EnvironmentConfig
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

        // Initialize Environment Config from BuildConfig
        EnvironmentConfig.initialize(
            dbKey = BuildConfig.DB_KEY,
            serverClientId = BuildConfig.SERVER_CLIENT_ID
        )

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
