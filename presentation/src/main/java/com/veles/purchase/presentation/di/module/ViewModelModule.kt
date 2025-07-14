package com.veles.purchase.presentation.di.module

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.login.LoginViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.photo.PhotoPurchaseComposeViewModel

/**
 * Koin module for ViewModels in presentation layer
 */
val viewModelModule = module {

    // Login ViewModel
    viewModel {
        LoginViewModel(
            router = get(),
            loginUseCase = get(),
            // Dependencies will be automatically injected by Koin
        )
    }

    // Photo Purchase ViewModel
    viewModel {
        PhotoPurchaseComposeViewModel(
            savedStateHandle = get(),
            router = get(),
            sharedFlowBus = get(),
            getPhotoUseCase = get(),
            // Dependencies will be automatically injected by Koin
        )
    }

    // Add more ViewModels here as needed
}
