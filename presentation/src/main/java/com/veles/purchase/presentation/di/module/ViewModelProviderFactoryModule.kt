package com.veles.purchase.presentation.di.module

import androidx.lifecycle.ViewModelProvider
import com.veles.purchase.presentation.base.mvvm.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ViewModelProviderFactoryModule {

    @Singleton
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
