package com.veles.purchase.presentation.presentation.mvvm.purchase.photo

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface PhotoPurchaseComposeModule {

    @Binds
    @IntoMap
    @ViewModelKey(PhotoPurchaseComposeViewModel::class)
    fun bindViewModel(viewModel: PhotoPurchaseComposeViewModel): ViewModel

    companion object {

        @Provides
        fun provideArgs(fragment: PhotoPurchaseComposeFragment): PhotoPurchaseComposeFragmentArgs {
            return fragment.navArgs<PhotoPurchaseComposeFragmentArgs>().value
        }
    }
}
