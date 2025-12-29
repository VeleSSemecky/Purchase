package com.veles.purchase.data.repository

import com.veles.purchase.data.repository.auth.AuthWithGoogleRepositoryImpl
import com.veles.purchase.data.repository.collection.CollectionPurchaseRepositoryImpl
import com.veles.purchase.data.repository.collection.CollectionRepositoryImpl
import com.veles.purchase.data.repository.history.HistoryRepositoryImpl
import com.veles.purchase.data.repository.purchase.PurchaseRepositoryImpl
import com.veles.purchase.data.repository.setting.SettingRepositoryImpl
import com.veles.purchase.data.repository.sku.SkuPhotoRepositoryImpl
import com.veles.purchase.data.repository.sku.SkuRepositoryImpl
import com.veles.purchase.data.repository.user.FirebaseGetUserRepositoryImpl
import com.veles.purchase.data.repository.user.FirebaseMessageTokenRepositoryImpl
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import com.veles.purchase.domain.repository.collection.CollectionPurchaseRepository
import com.veles.purchase.domain.repository.collection.CollectionRepository
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import com.veles.purchase.domain.repository.setting.SettingRepository
import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
import com.veles.purchase.domain.repository.sku.SkuRepository
import com.veles.purchase.domain.repository.user.FirebaseGetUserRepository
import com.veles.purchase.domain.repository.user.FirebaseMessageTokenRepository
import org.koin.dsl.module

val repositoryModule = module {
    // Auth repositories
    single<AuthWithGoogleRepository> {
        AuthWithGoogleRepositoryImpl(
            auth = get(),
            firestore = get()
        )
    }

    // User repositories
    single<FirebaseGetUserRepository> {
        FirebaseGetUserRepositoryImpl(
            firestore = get(),
            auth = get()
        )
    }
    single<FirebaseMessageTokenRepository> {
        FirebaseMessageTokenRepositoryImpl(
            firestore = get()
        )
    }

    // Purchase repositories
    single<PurchaseRepository> {
        PurchaseRepositoryImpl(
            firestore = get()
        )
    }

    // Collection repositories
    single<CollectionPurchaseRepository> {
        CollectionPurchaseRepositoryImpl(
            firestore = get(),
            auth = get()
        )
    }

    single<CollectionRepository> {
        CollectionRepositoryImpl(
            firestore = get(),
            auth = get(),
            collectionPurchaseRepository = get()
        )
    }

    // SKU repositories (Room-based)
    single<SkuRepository> {
        SkuRepositoryImpl(
            skuDAO = get()
        )
    }

    single<SkuPhotoRepository> {
        SkuPhotoRepositoryImpl(
            skuPhotoDAO = get()
        )
    }

    // History repository (Room-based)
    single<HistoryRepository> {
        HistoryRepositoryImpl(
            purchaseDAO = get()
        )
    }

    // Settings repository (In-memory for now, TODO: migrate to DataStore KMP)
    single<SettingRepository> {
        SettingRepositoryImpl()
    }
}

