package com.veles.purchase.presentation.di.module

import android.content.Context
import androidx.biometric.BiometricManager
import com.veles.purchase.config.EnvironmentConfig.ENCRYPTION_ALGORITHM
import com.veles.purchase.config.EnvironmentConfig.ENCRYPTION_BLOCK_MODE
import com.veles.purchase.config.EnvironmentConfig.ENCRYPTION_PADDING
import dagger.Module
import dagger.Provides
import javax.crypto.Cipher
import javax.inject.Singleton

@Module
object CryptographyModule {

    @Provides
    @Singleton
    fun provideCipher(): Cipher {
        val transformation = "$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING"
        return Cipher.getInstance(transformation)
    }

    @Provides
    @Singleton
    fun provideBiometricManager(context: Context): BiometricManager {
        return BiometricManager.from(context)
    }
}
