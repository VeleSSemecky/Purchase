package com.veles.purchase.domain.di

import com.veles.purchase.domain.repository.collection.CollectionRepository
import com.veles.purchase.domain.repository.collection.MockCollectionRepository
import com.veles.purchase.domain.repository.purchase.MockPurchaseRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import com.veles.purchase.domain.repository.setting.MockSettingRepository
import com.veles.purchase.domain.repository.setting.SettingRepository
import com.veles.purchase.domain.repository.sku.MockSkuPhotoRepository
import com.veles.purchase.domain.repository.sku.MockSkuRepository
import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
import com.veles.purchase.domain.repository.sku.SkuRepository

/**
 * Mock Domain Module - provides mock implementations of all repositories
 * for testing UI without real data layer
 */
object MockDomainModule {

    private val collectionRepository: CollectionRepository by lazy {
        MockCollectionRepository()
    }

    private val purchaseRepository: PurchaseRepository by lazy {
        MockPurchaseRepository()
    }

    private val skuRepository: SkuRepository by lazy {
        MockSkuRepository()
    }

    private val skuPhotoRepository: SkuPhotoRepository by lazy {
        MockSkuPhotoRepository()
    }

    private val settingRepository: SettingRepository by lazy {
        MockSettingRepository()
    }

    fun provideCollectionRepository(): CollectionRepository = collectionRepository

    fun providePurchaseRepository(): PurchaseRepository = purchaseRepository

    fun provideSkuRepository(): SkuRepository = skuRepository

    fun provideSkuPhotoRepository(): SkuPhotoRepository = skuPhotoRepository

    fun provideSettingRepository(): SettingRepository = settingRepository
}

