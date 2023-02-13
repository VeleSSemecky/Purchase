package com.veles.purchase.presentation.presentation.mvvm.purchase.sort

import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SortPurchaseModule {

    @Binds
    @IntoMap
    @ViewModelKey(SortPurchaseViewModel::class)
    fun bindViewModel(viewModel: SortPurchaseViewModel): ViewModel
}
