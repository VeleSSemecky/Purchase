package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface EditCollectionComposeModule {

    @Binds
    @IntoMap
    @ViewModelKey(EditCollectionComposeViewModel::class)
    fun bindViewModel(viewModel: EditCollectionComposeViewModel): ViewModel

    companion object {

        @Provides
        fun provideArgs(fragment: EditCollectionComposeFragment): EditCollectionComposeFragmentArgs {
            return fragment.navArgs<EditCollectionComposeFragmentArgs>().value
        }
    }
}
