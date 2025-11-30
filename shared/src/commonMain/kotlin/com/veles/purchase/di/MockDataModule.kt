package com.veles.purchase.di

import com.veles.purchase.domain.repository.collection.CollectionRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import com.veles.purchase.domain.repository.setting.SettingRepository
import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
import com.veles.purchase.domain.repository.sku.SkuRepository
import com.veles.purchase.domain.di.MockDomainModule
import org.koin.dsl.module

/**
 * Mock Data Module - provides mock implementations from mockDomain
 *
 * This module will be used in Phase 2-3 for UI development and testing.
 * In Phase 6, this will be replaced with real data implementations.
 */
val mockDataModule = module {

    // Collection Repository
    single<CollectionRepository> {
        MockDomainModule.provideCollectionRepository()
    }

    // Purchase Repository
    single<PurchaseRepository> {
        MockDomainModule.providePurchaseRepository()
    }

    // SKU Repository
    single<SkuRepository> {
        MockDomainModule.provideSkuRepository()
    }

    // SKU Photo Repository
    single<SkuPhotoRepository> {
        MockDomainModule.provideSkuPhotoRepository()
    }

    // Settings Repository
    single<SettingRepository> {
        MockDomainModule.provideSettingRepository()
    }
}

/**
 * Initialize Koin with mock data module
 * Call this in your Application.onCreate() (Android) or app initialization (iOS)
 */
fun initKoinMockData() = org.koin.core.context.startKoin {
    modules(mockDataModule)
}


