package com.veles.purchase.di

import com.veles.purchase.platform.auth.GoogleSignInHelper
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android platform module
 *
 * Provides Android-specific implementations:
 * - FirebaseInitializer (Firebase setup)
 * - GoogleSignInHelper (Google Sign-In)
 * - BiometricAuthenticator (using BiometricPrompt)
 * - NotificationManager (FCM - to be added)
 * - FileStorage (Android storage - to be added)
 * - etc.
 */
actual val platformModule: Module = module {

    // Firebase Initializer
//    single {
//        FirebaseInitializer().apply {
//            initialize(null)
//        }
//    }

    // Google Sign-In Helper
    // Note: Requires Activity and serverClientId
    // Activity will be provided from Android app context
    factory { (activity: android.app.Activity, serverClientId: String) ->
        GoogleSignInHelper(activity, serverClientId)
    }

    // TODO: Add other Android-specific dependencies
    // - FCM NotificationManager
    // - File storage
    // - Permissions manager
}
