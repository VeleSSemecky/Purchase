package com.veles.purchase.presentation.presentation.compose.shopping.photo

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface PhotoListModule {

    @Binds
    @IntoMap
    @ViewModelKey(PhotoListViewModel::class)
    fun bindViewModel(viewModel: PhotoListViewModel): ViewModel

    companion object {

        @Provides
        fun provideArgs(fragment: PhotoListFragment): PhotoListFragmentArgs {
            return fragment.navArgs<PhotoListFragmentArgs>().value
        }
    }
}
