package com.veles.purchase.presentation.di.module

import android.content.Context
import android.content.SharedPreferences
import com.veles.purchase.presentation.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PersistenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("${BuildConfig.FLAVOR}_preferences", Context.MODE_PRIVATE)
}
