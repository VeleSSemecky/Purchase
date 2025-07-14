package com.veles.purchase.presentation.di.module

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.presentation.data.loger.LoggerImpl
import org.koin.dsl.module

/**
 * Koin module for logger dependencies
 * Converted from Dagger LoggerModule
 */
val loggerModule = module {

    single<FirebaseCrashlytics> { Firebase.crashlytics }

    single<Logger> { LoggerImpl(get()) }
}
