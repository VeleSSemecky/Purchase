package com.veles.purchase.di

import com.veles.purchase.data.firebase.FirebaseInitializer
import com.veles.purchase.platform.biometric.BiometricAuthenticator
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android platform module
 *
 * Provides Android-specific implementations:
 * - FirebaseInitializer (Firebase setup)
 * - BiometricAuthenticator (using BiometricPrompt)
 * - NotificationManager (FCM - to be added)
 * - FileStorage (Android storage - to be added)
 * - etc.
 */
actual val platformModule: Module = module {

    // Firebase Initializer
    single {
        FirebaseInitializer().apply {
            initialize()
        }
    }

    // Biometric Authenticator
    // Note: Requires FragmentActivity to be provided from Android app
    // Will be injected in ViewModels that need biometric auth
    factory { (activity: androidx.fragment.app.FragmentActivity) ->
        BiometricAuthenticator(activity)
    }

    // TODO: Add other Android-specific dependencies
    // - FCM NotificationManager
    // - File storage
    // - Permissions manager
}

