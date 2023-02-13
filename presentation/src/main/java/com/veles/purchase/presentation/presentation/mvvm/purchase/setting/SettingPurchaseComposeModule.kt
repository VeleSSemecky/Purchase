package com.veles.purchase.presentation.presentation.mvvm.purchase.setting

import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingPurchaseComposeModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingPurchaseComposeViewModel::class)
    abstract fun bindViewModel(viewModel: SettingPurchaseComposeViewModel): ViewModel
}
