package com.veles.purchase.presentation.presentation.mvvm.purchase.edit

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface EditPurchaseModule {

    @Binds
    @IntoMap
    @ViewModelKey(EditPurchaseViewModel::class)
    fun bindViewModel(viewModel: EditPurchaseViewModel): ViewModel

    companion object {
        @Provides
        fun provideArgs(fragment: EditPurchaseFragment): EditPurchaseFragmentArgs {
            return fragment.navArgs<EditPurchaseFragmentArgs>().value
        }
    }
}
