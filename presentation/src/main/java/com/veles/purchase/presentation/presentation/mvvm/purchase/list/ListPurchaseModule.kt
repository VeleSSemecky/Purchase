package com.veles.purchase.presentation.presentation.mvvm.purchase.list

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ListPurchaseModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListPurchaseViewModel::class)
    fun bindViewModel(viewModel: ListPurchaseViewModel): ViewModel

    companion object {

        @Provides
        fun provideArgs(fragment: ListPurchaseFragment): ListPurchaseFragmentArgs {
            return fragment.navArgs<ListPurchaseFragmentArgs>().value
        }
    }
}
