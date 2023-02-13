package com.veles.purchase.presentation.presentation.mvvm.purchase.login

import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindViewModel(viewModel: LoginViewModel): ViewModel
}
