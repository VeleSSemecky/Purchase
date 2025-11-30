package com.veles.purchase.di

import com.veles.purchase.presentation.mvvm.purchase.collection.CollectionPurchaseViewModel
import com.veles.purchase.presentation.mvvm.purchase.edit.PurchaseEditViewModel
import com.veles.purchase.presentation.mvvm.purchase.list.PurchaseListViewModel
import com.veles.purchase.presentation.mvvm.purchase.setting.SettingsPurchaseViewModel
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

    // TODO Phase 2.2+: Add more ViewModels as they are migrated
    // viewModel { BiometricComposeViewModel(get(), get()) }
    // etc.
}

