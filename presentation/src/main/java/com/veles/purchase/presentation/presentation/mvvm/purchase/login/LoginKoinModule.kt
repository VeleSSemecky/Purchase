package com.veles.purchase.presentation.presentation.mvvm.purchase.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for Login feature
 *
 * BEFORE (Dagger):
 * @Module
 * interface LoginModule {
 *     @Binds
 *     @IntoMap
 *     @ViewModelKey(LoginViewModel::class)
 *     fun bindViewModel(viewModel: LoginViewModel): ViewModel
 * }
 *
 * AFTER (Koin):
 * Simple DSL-based module definition
 */
val loginKoinModule = module {

    // ViewModel - автоматично інжектить залежності
    viewModel {
        LoginViewModel(
            router = get(),
            loginUseCase = get()
        )
    }
}
