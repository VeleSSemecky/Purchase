package com.veles.purchase.presentation.di.module

import android.content.ContentResolver
import com.veles.purchase.data.local.store.SettingsDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidComponentModule = module {

    single<ContentResolver> { androidContext().contentResolver }
    single<SettingsDataStore> { SettingsDataStore(get()) }
}
