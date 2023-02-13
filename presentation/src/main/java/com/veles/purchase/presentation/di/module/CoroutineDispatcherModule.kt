package com.veles.purchase.presentation.di.module

import com.veles.purchase.domain.core.dispatcher.AppCoroutineDispatcher
import com.veles.purchase.domain.core.dispatcher.AppCoroutineDispatcherImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CoroutineDispatcherModule {

    @Singleton
    @Binds
    fun bindCoroutineDispatcher(coroutineDispatcher: AppCoroutineDispatcherImpl): AppCoroutineDispatcher
}
