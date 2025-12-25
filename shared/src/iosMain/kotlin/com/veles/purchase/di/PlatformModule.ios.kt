package com.veles.purchase.di

import com.veles.purchase.data.firebase.FirebaseInitializer
import com.veles.purchase.platform.biometric.BiometricAuthenticator
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * iOS platform module
 *
 * Provides iOS-specific implementations (stubs for Phase 2-3)
 * Will be fully implemented in Phase 5
 */
actual val platformModule: Module = module {

    // Firebase Initializer
    single {
        FirebaseInitializer().apply {
            initialize()
        }
    }

    // Biometric Authenticator (stub for now)
    factory {
        BiometricAuthenticator()
    }

    // TODO Phase 5: Add iOS-specific dependencies
    // - LocalAuthentication biometric
    // - APNs NotificationManager
    // - iOS file storage
    // - iOS permissions
}

