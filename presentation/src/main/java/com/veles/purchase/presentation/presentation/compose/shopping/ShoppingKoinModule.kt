package com.veles.purchase.presentation.presentation.compose.shopping

import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.choose.CurrencyChooseViewModel
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.search.CurrencySearchViewModel
import com.veles.purchase.presentation.presentation.compose.shopping.edit.SkuEditViewModel
import com.veles.purchase.presentation.presentation.compose.shopping.graph.OutlayGraphViewModel
import com.veles.purchase.presentation.presentation.compose.shopping.photo.PhotoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

/**
 * Koin module for Shopping-related features
 * Combines multiple shopping modules for better organization
 *
 * BEFORE (Dagger):
 * Multiple separate modules for each shopping feature
 *
 * AFTER (Koin):
 * Consolidated module with logical grouping
 */
val shoppingKoinModule = module {

    // PhotoList ViewModel
    viewModel {
        PhotoListViewModel(
            deleteSkuPhotoUseCase = get(),
            savedStateHandle = get(),
            sharedFlowBus = get(),
        )
    }

    // SkuEdit ViewModel
    viewModel {
        SkuEditViewModel(
            setSkuUseCase = get(),
            getSkuUseCase = get(),
            getSkuPhotoUseCase = get(),
            savedStateHandle = get(),
            sharedFlowBus = get(),
        )
    }

    // OutlayGraph ViewModel
    viewModel {
        OutlayGraphViewModel(
            getSkuSumMontUseCase = get(),
            sharedFlowBus = get(),
        )
    }

    // Currency Choose ViewModel
    viewModel {
        CurrencyChooseViewModel(
            sharedFlowBus = get(),
            savedStateHandle = get(),
        )
    }

    // Currency Search ViewModel
    viewModel {
        CurrencySearchViewModel(
            sharedFlowBus = get(),
            savedStateHandle = get(),
        )
    }
}
