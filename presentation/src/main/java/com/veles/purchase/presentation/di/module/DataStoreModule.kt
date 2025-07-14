package com.veles.purchase.presentation.di.module

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.data.local.data.DataStoreImpl
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

/**
 * Koin module for DataStore dependencies
 * Converted from Dagger DataStoreModule
 */
val dataStoreModule = module {

    single<androidx.datastore.core.DataStore<Preferences>> {
        androidContext().dataStore
    }

    single<DataStore> { DataStoreImpl(get()) }
}

private val Context.dataStore by preferencesDataStore(
    name = "USER_PREFERENCES_NAME"
)
