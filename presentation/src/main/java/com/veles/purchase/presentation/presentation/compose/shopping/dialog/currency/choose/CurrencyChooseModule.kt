package com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.choose

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface CurrencyChooseModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyChooseViewModel::class)
    fun bindViewModel(viewModel: CurrencyChooseViewModel): ViewModel

    companion object {
        @Provides
        fun provideArgs(fragment: CurrencyChooseFragment): CurrencyChooseFragmentArgs {
            return fragment.navArgs<CurrencyChooseFragmentArgs>().value
        }
    }
}
