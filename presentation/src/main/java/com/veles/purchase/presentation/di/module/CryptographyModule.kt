package com.veles.purchase.presentation.di.module

import androidx.biometric.BiometricManager
import com.veles.purchase.config.EnvironmentConfig.ENCRYPTION_ALGORITHM
import com.veles.purchase.config.EnvironmentConfig.ENCRYPTION_BLOCK_MODE
import com.veles.purchase.config.EnvironmentConfig.ENCRYPTION_PADDING
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import javax.crypto.Cipher

/**
 * Koin module for cryptography dependencies
 * Converted from Dagger CryptographyModule
 */
val cryptographyModule = module {

    single<Cipher> {
        val transformation = "$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING"
        Cipher.getInstance(transformation)
    }

    single<BiometricManager> {
        BiometricManager.from(androidContext())
    }
}
