package com.veles.purchase.presentation.presentation.mvvm.pip

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for PIP (Picture-in-Picture) feature
 *
 * BEFORE (Dagger):
 * @Module
 * interface PIPModule {
 *     @Binds
 *     @IntoMap
 *     @ViewModelKey(PIPViewModel::class)
 *     fun bindViewModel(viewModel: PIPViewModel): ViewModel
 * }
 *
 * AFTER (Koin):
 * Simple DSL-based module definition
 */
val pipKoinModule = module {

    // PIP ViewModel
    viewModel {
        PIPViewModel(
            logger = get(),
            contentResolver = get(),
            remoteActionBroadcastReceiver = get(),
        )
    }
}
