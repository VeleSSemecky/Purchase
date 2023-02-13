package com.veles.purchase.presentation.presentation.mvvm.purchase.list

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ListPurchaseComposeModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListPurchaseComposeViewModel::class)
    fun bindViewModel(viewModel: ListPurchaseComposeViewModel): ViewModel

    companion object {

        @Provides
        fun provideArgs(fragment: ListPurchaseComposeFragment): ListPurchaseComposeFragmentArgs {
            return fragment.navArgs<ListPurchaseComposeFragmentArgs>().value
        }
    }
}
