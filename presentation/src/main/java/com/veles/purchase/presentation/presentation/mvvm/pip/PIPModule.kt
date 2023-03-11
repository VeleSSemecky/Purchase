package com.veles.purchase.presentation.presentation.mvvm.pip

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.util.concurrent.Executor

@Module
interface PIPModule {

    @PIPScope
    @Binds
    @IntoMap
    @ViewModelKey(PIPViewModel::class)
    fun provideViewModel(viewModel: PIPViewModel): ViewModel

}
