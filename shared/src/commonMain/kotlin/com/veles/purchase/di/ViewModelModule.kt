package com.veles.purchase.di

import com.veles.purchase.presentation.mvvm.purchase.category.CategoryViewModel
import com.veles.purchase.presentation.mvvm.purchase.collection.CollectionMembersViewModel
import com.veles.purchase.presentation.mvvm.purchase.collection.CollectionPurchaseComposeViewModel
import com.veles.purchase.presentation.mvvm.purchase.collection.EditCollectionComposeViewModel
import com.veles.purchase.presentation.mvvm.purchase.edit.EditPurchaseViewModel
import com.veles.purchase.presentation.mvvm.purchase.history.HistoryComposeViewModel
import com.veles.purchase.presentation.mvvm.purchase.later.ListLaterPurchaseViewModel
import com.veles.purchase.presentation.mvvm.purchase.list.ListPurchaseViewModel
import com.veles.purchase.presentation.mvvm.purchase.setting.SettingPurchaseComposeViewModel
import com.veles.purchase.presentation.mvvm.sku.edit.SkuEditViewModel
import com.veles.purchase.presentation.mvvm.sku.list.SkuListViewModel
import com.veles.purchase.presentation.mvvm.sku.statistics.OutlayGraphViewModel
import com.veles.purchase.presentation.viewmodel.login.LoginViewModel
import com.veles.purchase.presentation.viewmodel.main.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModels Module - provides all ViewModels for the application
 * Phase 6: ViewModels migrated to UseCases (Clean Architecture)
 */
val viewModelModule = module {

    // Login ViewModel
    viewModel {
        LoginViewModel(
            authRepository = get()
        )
    }

    // Main ViewModel — user info + logout
    viewModel {
        MainViewModel(
            auth = get(),
            logoutRepository = get()
        )
    }

    // SettingPurchaseComposeViewModel
    viewModel {
        SettingPurchaseComposeViewModel(
            getSettingUseCase = get(),
            setSettingUseCase = get()
        )
    }

    // CollectionPurchaseComposeViewModel
    viewModel {
        CollectionPurchaseComposeViewModel(
            firebaseFirestorePurchaseCollectionUseCase = get(),
            deletePurchaseCollectionUseCase = get()
        )
    }

    // EditCollectionComposeViewModel
    viewModel { parameters ->
        EditCollectionComposeViewModel(
            collectionId = parameters.get(),
            getCollectionPurchaseUseCase = get(),
            setCollectionPurchaseUseCase = get(),
            savedStateHandle = get()
        )
    }

    // CollectionMembersViewModel
    viewModel { parameters ->
        CollectionMembersViewModel(
            initialSelectedIds = parameters.get(),
            userUseCase = get()
        )
    }

    // CategoryViewModel
    viewModel { parameters ->
        CategoryViewModel(
            collectionId = parameters.get(),
            getCollectionPurchaseUseCase = get(),
            savePurchaseCategoryUseCase = get()
        )
    }

    // ListPurchaseViewModel
    viewModel { parameters ->
        ListPurchaseViewModel(
            collectionId = parameters.get(),
            getPurchasesUseCase = get(),
            savePurchaseUseCase = get(),
            checkPurchaseUseCase = get(),
            deletePurchaseUseCase = get(),
            getCollectionPurchaseUseCase = get(),
            getSettingUseCase = get()
        )
    }

    // EditPurchaseViewModel
    viewModel { parameters ->
        EditPurchaseViewModel(
            collectionId = parameters.get(),
            purchaseId = parameters.get(),
            getPurchaseUseCase = get(),
            savePurchaseUseCase = get(),
            getCollectionPurchaseUseCase = get(),
            uploadPurchasePhotosUseCase = get(),
            deletePurchasePhotoRepository = get()
        )
    }

    // HistoryComposeViewModel
    viewModel { parameters ->
        HistoryComposeViewModel(
            collectionId = parameters.get(),
            getPurchaseHistoryUseCase = get()
        )
    }

    // ListLaterPurchaseViewModel
    viewModel { parameters ->
        ListLaterPurchaseViewModel(
            collectionId = parameters.get(),
            getPurchasesUseCase = get(),
            savePurchaseUseCase = get(),
            checkPurchaseUseCase = get(),
            deletePurchaseUseCase = get(),
            getCollectionPurchaseUseCase = get(),
            getSettingUseCase = get()
        )
    }

    // SkuListViewModel
    viewModel {
        SkuListViewModel(
            getSkuUseCase = get(),
            deleteSkuUseCase = get()
        )
    }

    // SkuEditViewModel
    viewModel { parameters ->
        SkuEditViewModel(
            skuId = parameters.getOrNull(),
            getSkuUseCase = get(),
            setSkuUseCase = get()
        )
    }

    // OutlayGraphViewModel
    viewModel {
        OutlayGraphViewModel(
            getSkuSumMontUseCase = get()
        )
    }
}
