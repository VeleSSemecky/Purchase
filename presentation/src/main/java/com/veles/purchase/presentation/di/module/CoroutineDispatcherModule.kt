package com.veles.purchase.presentation.di.module

import com.veles.purchase.domain.core.dispatcher.AppCoroutineDispatcher
import com.veles.purchase.domain.core.dispatcher.AppCoroutineDispatcherImpl
import org.koin.dsl.module

/**
 * Koin module for coroutine dispatcher dependencies
 * Converted from Dagger CoroutineDispatcherModule
 */
val coroutineDispatcherModule = module {

    single<AppCoroutineDispatcher> { AppCoroutineDispatcherImpl() }
}
