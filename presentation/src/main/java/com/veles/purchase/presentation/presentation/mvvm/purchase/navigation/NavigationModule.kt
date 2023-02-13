package com.veles.purchase.presentation.presentation.mvvm.purchase.navigation

import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NavigationModule {

    @Binds
    @IntoMap
    @ViewModelKey(NavigationViewModel::class)
    fun bindViewModel(viewModel: NavigationViewModel): ViewModel
}
