package com.veles.purchase.presentation.di.module

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.data.local.data.DataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataStoreModule {

    @Binds
    @Singleton
    fun bindDataStore(dataStore: DataStoreImpl): DataStore

    companion object {

        private val Context.dataStore by preferencesDataStore(
            name = "USER_PREFERENCES_NAME"
        )

        @Provides
        fun provideDataStore(context: Context): androidx.datastore.core.DataStore<Preferences> {
            return context.dataStore
        }
    }
}
