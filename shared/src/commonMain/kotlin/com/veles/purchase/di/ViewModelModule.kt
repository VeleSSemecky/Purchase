package com.veles.purchase.di

import com.veles.purchase.presentation.mvvm.purchase.biometric.BiometricViewModel
import com.veles.purchase.presentation.mvvm.purchase.category.CategoryViewModel
import com.veles.purchase.presentation.mvvm.purchase.collection.CollectionEditViewModel
import com.veles.purchase.presentation.mvvm.purchase.collection.CollectionPurchaseViewModel
import com.veles.purchase.presentation.mvvm.purchase.edit.PurchaseEditViewModel
import com.veles.purchase.presentation.mvvm.purchase.history.HistoryViewModel
import com.veles.purchase.presentation.mvvm.purchase.later.ListLaterViewModel
import com.veles.purchase.presentation.mvvm.purchase.list.PurchaseListViewModel
import com.veles.purchase.presentation.mvvm.purchase.setting.SettingsPurchaseViewModel
import com.veles.purchase.presentation.mvvm.sku.edit.SkuEditViewModel
import com.veles.purchase.presentation.mvvm.sku.list.SkuListViewModel
import com.veles.purchase.presentation.mvvm.sku.statistics.SkuStatisticsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModels Module - provides all ViewModels for the application
 *
 * Phase 2.2-2.7: ViewModels Migration
 *
 * Migrated ViewModels:
 * - SettingsPurchaseViewModel ✅ (Phase 2.2)
 * - CollectionPurchaseViewModel ✅ (Phase 2.5)
 * - PurchaseListViewModel ✅ (Phase 2.6)
 * - PurchaseEditViewModel ✅ (Phase 2.7)
 *
 * TODO: Migrate remaining ViewModels (~11 more)
 * - BiometricComposeViewModel
 * - etc.
 */
val viewModelModule = module {

    // Settings ViewModel
    viewModel {
        SettingsPurchaseViewModel(
            settingRepository = get()
        )
    }

    // Collection ViewModel
    viewModel {
        CollectionPurchaseViewModel(
            collectionRepository = get()
        )
    }

    // Collection Edit ViewModel (requires collectionId parameter)
    viewModel { parameters ->
        CollectionEditViewModel(
            collectionId = parameters.get(),
            collectionRepository = get()
        )
    }

    // Purchase List ViewModel (requires collectionId parameter)
    viewModel { parameters ->
        PurchaseListViewModel(
            collectionId = parameters.get(),
            purchaseRepository = get(),
            collectionRepository = get(),
            settingRepository = get()
        )
    }

    // Purchase Edit ViewModel (requires collectionId and purchaseId parameters)
    viewModel { parameters ->
        PurchaseEditViewModel(
            collectionId = parameters.get(),
            purchaseId = parameters.get(),
            purchaseRepository = get(),
            collectionRepository = get()
        )
    }

    // Category ViewModel (requires collectionId parameter)
    viewModel { parameters ->
        CategoryViewModel(
            collectionId = parameters.get(),
            collectionRepository = get()
        )
    }

    // History ViewModel (requires collectionId parameter)
    viewModel { parameters ->
        HistoryViewModel(
            collectionId = parameters.get(),
            historyRepository = get()
        )
    }

    // Biometric ViewModel (requires BiometricAuthenticator parameter from activity)
    viewModel { parameters ->
        BiometricViewModel(
            biometricAuthenticator = parameters.get()
        )
    }

    // List Later ViewModel (requires collectionId parameter)
    viewModel { parameters ->
        ListLaterViewModel(
            collectionId = parameters.get(),
            purchaseRepository = get(),
            collectionRepository = get(),
            settingRepository = get()
        )
    }

    // SKU List ViewModel
    viewModel {
        SkuListViewModel(
            skuRepository = get()
        )
    }

    // SKU Edit ViewModel (requires skuId parameter - nullable for new SKU)
    viewModel { parameters ->
        SkuEditViewModel(
            skuId = parameters.getOrNull(),
            skuRepository = get()
        )
    }

    // SKU Statistics ViewModel (Outlay Graph)
    viewModel {
        SkuStatisticsViewModel(
            skuRepository = get()
        )
    }

    // TODO Phase 2.2+: Add more ViewModels as they are migrated
    // etc.
}

