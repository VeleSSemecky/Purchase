package com.veles.purchase.presentation.presentation.compose.shopping.edit

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface SkuEditModule {

    @Binds
    @IntoMap
    @ViewModelKey(SkuEditViewModel::class)
    fun bindViewModel(viewModel: SkuEditViewModel): ViewModel

    companion object {

        @Provides
        fun provideArgs(fragment: SkuEditFragment): SkuEditFragmentArgs {
            return fragment.navArgs<SkuEditFragmentArgs>().value
        }
    }
}
