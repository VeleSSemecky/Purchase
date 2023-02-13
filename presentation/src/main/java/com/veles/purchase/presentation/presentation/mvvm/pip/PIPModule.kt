package com.veles.purchase.presentation.presentation.mvvm.pip

import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PIPModule {

    @PIPScope
    @Binds
    @IntoMap
    @ViewModelKey(PIPViewModel::class)
    fun provideViewModel(viewModel: PIPViewModel): ViewModel
}
