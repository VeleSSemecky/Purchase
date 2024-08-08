package com.veles.purchase.presentation.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.veles.purchase.presentation.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PersistenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
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
