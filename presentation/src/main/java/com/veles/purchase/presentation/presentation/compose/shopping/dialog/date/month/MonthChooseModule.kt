package com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.month

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface MonthChooseModule {

    @Binds
    @IntoMap
    @ViewModelKey(MonthChooseViewModel::class)
    fun bindViewModel(viewModel: MonthChooseViewModel): ViewModel

    companion object {
        @Provides
        fun provideArgs(fragment: MonthChooseFragment): MonthChooseFragmentArgs {
            return fragment.navArgs<MonthChooseFragmentArgs>().value
        }
    }
}
