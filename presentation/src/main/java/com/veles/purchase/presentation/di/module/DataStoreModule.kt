package com.veles.purchase.presentation.di.module

import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.data.local.data.DataStoreImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataStoreModule {

    @Binds
    @Singleton
    fun bindDataStore(dataStore: DataStoreImpl): DataStore
}
