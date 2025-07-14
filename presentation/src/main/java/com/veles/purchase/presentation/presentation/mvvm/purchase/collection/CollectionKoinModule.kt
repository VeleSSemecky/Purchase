package com.veles.purchase.presentation.presentation.mvvm.purchase.collection

import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.EditCollectionComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category.CategoryViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list.CollectionPurchaseComposeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for Collection-related features
 * Consolidates collection management modules
 *
 * BEFORE (Dagger):
 * Separate modules: CollectionPurchaseComposeModule, EditCollectionComposeModule, CategoryModule
 *
 * AFTER (Koin):
 * Single consolidated module with logical grouping
 */
val collectionKoinModule = module {

    // CollectionPurchaseCompose ViewModel
    viewModel {
        CollectionPurchaseComposeViewModel(
            firebaseFirestorePurchaseCollectionUseCase = get(),
            deletePurchaseCollectionUseCase = get(),
        )
    }

    // EditCollectionCompose ViewModel
    viewModel {
        EditCollectionComposeViewModel(
            savedStateHandle = get(),
            setCollectionPurchaseUseCase = get(),
            firebaseFirestorePurchaseCollectionUseCase = get(),
            userUseCase = get(),
            router = get(),
        )
    }

    // Category ViewModel
    viewModel {
        CategoryViewModel(
            savedStateHandle = get(),
            savePurchaseCategoryUseCase = get(),
            router = get(),
        )
    }
}
