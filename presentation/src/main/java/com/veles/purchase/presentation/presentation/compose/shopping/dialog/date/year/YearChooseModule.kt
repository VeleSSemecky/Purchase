package com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.year

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface YearChooseModule {

    @Binds
    @IntoMap
    @ViewModelKey(YearChooseViewModel::class)
    fun bindViewModel(viewModel: YearChooseViewModel): ViewModel

    companion object {
        @Provides
        fun provideArgs(fragment: YearChooseFragment): YearChooseFragmentArgs {
            return fragment.navArgs<YearChooseFragmentArgs>().value
        }
    }
}
