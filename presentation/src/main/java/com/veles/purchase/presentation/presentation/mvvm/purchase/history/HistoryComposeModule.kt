package com.veles.purchase.presentation.presentation.mvvm.purchase.history

import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HistoryComposeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HistoryComposeViewModel::class)
    fun bindViewModel(viewModel: HistoryComposeViewModel): ViewModel
}
