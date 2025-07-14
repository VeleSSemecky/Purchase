package com.veles.purchase.presentation.presentation.mvvm.purchase.navigation

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for Navigation feature
 *
 * BEFORE (Dagger):
 * @Module
 * interface NavigationModule {
 *     @Binds
 *     @IntoMap
 *     @ViewModelKey(NavigationViewModel::class)
 *     fun bindViewModel(viewModel: NavigationViewModel): ViewModel
 *
 *     @Binds
 *     @IntoMap
 *     @ViewModelKey(UpdateViewModel::class)
 *     fun bindUpdateViewModel(viewModel: UpdateViewModel): ViewModel
 * }
 *
 * AFTER (Koin):
 * Multiple ViewModels in one module
 */
val navigationKoinModule = module {

    // NavigationViewModel
    viewModel {
        NavigationViewModel(
            userUseCase = get(),
            logoutUseCase = get(),
            router = get(),
        )
    }

    // UpdateViewModel
    viewModel {
        UpdateViewModel(
            appUpdateHandler = get()
        )
    }
}
