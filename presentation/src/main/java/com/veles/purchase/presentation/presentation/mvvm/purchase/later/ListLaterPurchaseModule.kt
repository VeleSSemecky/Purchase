package com.veles.purchase.presentation.presentation.mvvm.purchase.later

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import com.veles.purchase.presentation.presentation.mvvm.purchase.list.ListPurchaseFragmentArgs
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ListLaterPurchaseModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListLaterPurchaseViewModel::class)
    fun bindViewModel(viewModel: ListLaterPurchaseViewModel): ViewModel

    companion object {

        @Provides
        fun provideArgs(fragment: ListLaterPurchaseFragment): ListPurchaseFragmentArgs {
            return fragment.navArgs<ListPurchaseFragmentArgs>().value
        }
    }
}
