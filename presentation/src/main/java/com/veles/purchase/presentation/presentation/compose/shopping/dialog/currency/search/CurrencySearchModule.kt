package com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.search

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface CurrencySearchModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencySearchViewModel::class)
    fun bindViewModel(viewModel: CurrencySearchViewModel): ViewModel

    companion object {
        @Provides
        fun provideArgs(fragment: CurrencySearchFragment): CurrencySearchFragmentArgs {
            return fragment.navArgs<CurrencySearchFragmentArgs>().value
        }
    }
}
