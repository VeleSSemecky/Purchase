package com.veles.purchase.presentation.presentation.mvvm.purchase

import com.veles.purchase.presentation.presentation.mvvm.purchase.edit.EditPurchaseViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.list.ListPurchaseViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.history.HistoryComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.sort.SortPurchaseViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.later.ListLaterPurchaseViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.setting.SettingPurchaseComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.biometric.BiometricComposeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

/**
 * Koin module for Purchase-related features
 * Consolidates multiple purchase modules for better organization
 *
 * BEFORE (Dagger):
 * Separate modules: EditPurchaseModule, ListPurchaseModule,
 * HistoryComposeModule, SortPurchaseModule, ListLaterPurchaseModule
 *
 * AFTER (Koin):
 * Single consolidated module with logical grouping
 */
val purchaseKoinModule = module {

    // EditPurchase ViewModel
    viewModel {
        EditPurchaseViewModel(
            savedStateHandle = get(),
            sharedFlowBus = get(),
            getPurchaseUseCase = get(),
            getPhotoUseCase = get(),
            savePurchaseUseCase = get(),
            getCollectionPurchaseCategoryUseCase = get(),
            router = get()
        )
    }

    // ListPurchase ViewModel
    viewModel {
        ListPurchaseViewModel(
            savedStateHandle = get(),
            sharedFlowBus = get(),
            getPurchasesUseCase = get(),
            deletePurchaseUseCase = get(),
            addLazyPurchaseUseCase = get(),
            checkPurchaseUseCase = get(),
            getCollectionPurchaseUseCase = get(),
            getSettingUseCase = get(),
            moveForLaterPurchaseUseCase = get(),
            router = get()
        )
    }

    // HistoryCompose ViewModel
    viewModel {
        HistoryComposeViewModel(
            savedStateHandle = get(),
            getPurchaseHistoryUseCase = get()
        )
    }

    // SortPurchase ViewModel
    viewModel {
        SortPurchaseViewModel(
            sharedFlowBus = get(),
            router = get()
        )
    }

    // ListLaterPurchase ViewModel
    viewModel {
        ListLaterPurchaseViewModel(
            savedStateHandle = get(),
            sharedFlowBus = get(),
            deletePurchaseUseCase = get(),
            addLazyPurchaseUseCase = get(),
            checkPurchaseUseCase = get(),
            getCollectionPurchaseUseCase = get(),
            getSettingUseCase = get(),
            router = get()
        )
    }

    // SettingPurchaseCompose ViewModel
    viewModel {
        SettingPurchaseComposeViewModel(
            getSettingUseCase = get(),
            setSettingUseCase = get(),
            router = get(),
        )
    }

    // BiometricCompose ViewModel
    viewModel {
        BiometricComposeViewModel(
            encryptionUseCase = get(),
            decryptionUseCase = get()
        )
    }
}
