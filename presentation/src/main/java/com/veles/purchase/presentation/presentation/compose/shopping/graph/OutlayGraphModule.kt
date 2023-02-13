package com.veles.purchase.presentation.presentation.compose.shopping.graph

import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OutlayGraphModule {

    @Binds
    @IntoMap
    @ViewModelKey(OutlayGraphViewModel::class)
    fun bindViewModel(viewModel: OutlayGraphViewModel): ViewModel
}
