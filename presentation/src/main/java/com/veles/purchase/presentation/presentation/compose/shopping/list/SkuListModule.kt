package com.veles.purchase.presentation.presentation.compose.shopping.list

import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SkuListModule {

    @Binds
    @IntoMap
    @ViewModelKey(SkuListViewModel::class)
    fun bindViewModel(viewModel: SkuListViewModel): ViewModel
}
