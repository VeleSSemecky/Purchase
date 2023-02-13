package com.veles.purchase.presentation.di.module

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.presentation.data.loger.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface LoggerModule {

    @Binds
    @Singleton
    fun bindLogger(logger: LoggerImpl): Logger

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseCrashlytics(): FirebaseCrashlytics {
            return Firebase.crashlytics
        }
    }
}
