package com.veles.purchase.presentation.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.veles.purchase.presentation.BuildConfig
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

/**
 * Koin module for persistence dependencies
 * Converted from Dagger PersistenceModule
 */
val persistenceModule = module {

    single<SharedPreferences> {
        val context = androidContext()
        when {
            BuildConfig.DEBUG -> context.getSharedPreferences("${BuildConfig.FLAVOR}_preferences", Context.MODE_PRIVATE)
            else -> EncryptedSharedPreferences.create(
                "${BuildConfig.FLAVOR}_preferences",
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }
}
