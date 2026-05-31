package com.veles.app

import android.app.Application
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.data.firebase.FirebaseInitializer
import com.veles.purchase.di.appModules
import com.veles.purchase.platform.logger.AppLogger
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

        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            AppLogger.e("CRASH", "Uncaught exception: ${e.message}", e)
            defaultHandler?.uncaughtException(thread, e)
        }

        // Initialize Environment Config from BuildConfig
        EnvironmentConfig.initialize(
            dbKey = BuildConfig.DB_KEY,
            serverClientId = BuildConfig.SERVER_CLIENT_ID,
            cloudinaryCloudName = BuildConfig.CLOUDINARY_CLOUD_NAME,
            cloudinaryApiKey = BuildConfig.CLOUDINARY_API_KEY,
            cloudinaryApiSecret = BuildConfig.CLOUDINARY_API_SECRET
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
