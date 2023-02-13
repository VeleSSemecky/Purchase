package com.veles.purchase.presentation.presentation.mvvm.purchase.biometric

import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class BiometricComposeModule {

    @Binds
    @IntoMap
    @ViewModelKey(BiometricComposeViewModel::class)
    abstract fun bindViewModel(viewModel: BiometricComposeViewModel): ViewModel
}
