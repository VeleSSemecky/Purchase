package com.veles.purchase.presentation.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.veles.purchase.data.local.cryptography.CryptographyManager
import com.veles.purchase.data.repository.auth.AuthWithGoogleRepositoryImpl
import com.veles.purchase.data.repository.auth.BiometricRepositoryImpl
import com.veles.purchase.data.repository.collection.delete.DeleteCollectionPurchaseRepositoryImpl
import com.veles.purchase.data.repository.collection.get.GetCollectionPurchaseRepositoryImpl
import com.veles.purchase.data.repository.collection.set.CollectionPurchaseRepositoryImpl
import com.veles.purchase.data.repository.history.HistoryRepositoryImpl
import com.veles.purchase.data.repository.later.PurchaseLaterRepositoryImpl
import com.veles.purchase.data.repository.message.NotificationMessageRepositoryImpl
import com.veles.purchase.data.repository.purchase.PurchaseRepositoryImpl
import com.veles.purchase.data.repository.setting.SettingRepositoryImpl
import com.veles.purchase.data.repository.sku.SkuPhotoRepositoryImpl
import com.veles.purchase.data.repository.sku.SkuRepositoryImpl
import com.veles.purchase.data.repository.storage.delete.DeletePurchasePhotoRepositoryImpl
import com.veles.purchase.data.repository.storage.get.GetPurchasePhotoRepositoryImpl
import com.veles.purchase.data.repository.storage.set.SetPurchasePhotoRepositoryImpl
import com.veles.purchase.data.repository.user.get.FirebaseGetUserRepositoryImpl
import com.veles.purchase.data.repository.auth.LogoutRepositoryImpl
import com.veles.purchase.data.repository.collection.category.PurchaseCategoryRepositoryImpl
import com.veles.purchase.data.repository.user.token.FirebaseMessageTokenRepositoryImpl
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import com.veles.purchase.domain.repository.auth.BiometricRepository
import com.veles.purchase.domain.repository.collection.DeleteCollectionPurchaseRepository
import com.veles.purchase.domain.repository.collection.GetCollectionPurchaseRepository
import com.veles.purchase.domain.repository.collection.CollectionPurchaseRepository
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.message.NotificationMessageRepository
import com.veles.purchase.domain.repository.purchase.GetPurchasePhotoRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import com.veles.purchase.domain.repository.purchase.PurchaseLaterRepository
import com.veles.purchase.domain.repository.setting.SettingRepository
import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
import com.veles.purchase.domain.repository.sku.SkuRepository
import com.veles.purchase.domain.repository.storage.DeletePurchasePhotoRepository
import com.veles.purchase.domain.repository.storage.SetPurchasePhotoRepository
import com.veles.purchase.domain.repository.user.FirebaseGetUserRepository
import com.veles.purchase.domain.repository.user.FirebaseMessageTokenRepository
import com.veles.purchase.domain.repository.auth.LogoutRepository
import com.veles.purchase.domain.repository.purchase.PurchaseCategoryRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module for repositories
 * Converted from Dagger RepositoryModule
 */
val repositoryModule = module {

    // Firebase services
    single<FirebaseStorage> { Firebase.storage }
    single<FirebaseFirestore> { Firebase.firestore }
    single<FirebaseAuth> { Firebase.auth }

    // Repository implementations
    single<NotificationMessageRepository> {
        NotificationMessageRepositoryImpl(
            notificationMessageService = get(),
            firebaseFirestore = get<FirebaseFirestore>(),
            dataStore = get()
        )
    }
    single<GetPurchasePhotoRepository> {
        GetPurchasePhotoRepositoryImpl(get<FirebaseStorage>())
    }
    single<HistoryRepository> {
        HistoryRepositoryImpl(get())
    }
    single<GetCollectionPurchaseRepository> {
        GetCollectionPurchaseRepositoryImpl(
            firebaseFirestore = get<FirebaseFirestore>(),
            firebaseAuth = get<FirebaseAuth>()
        )
    }
    single<CollectionPurchaseRepository> {
        CollectionPurchaseRepositoryImpl(
            firebaseFirestore = get<FirebaseFirestore>(),
            firebaseAuth = get<FirebaseAuth>()
        )
    }
    single<FirebaseGetUserRepository> {
        FirebaseGetUserRepositoryImpl(
            firebaseFirestore = get<FirebaseFirestore>(),
            firebaseAuth = get<FirebaseAuth>()
        )
    }
    single<FirebaseMessageTokenRepository> {
        FirebaseMessageTokenRepositoryImpl(get<FirebaseFirestore>())
    }
    single<SkuPhotoRepository> {
        SkuPhotoRepositoryImpl(get())
    }
    single<SkuRepository> {
        SkuRepositoryImpl(get())
    }
    single<LogoutRepository> {
        LogoutRepositoryImpl(androidContext(), get())
    }
    single<AuthWithGoogleRepository> {
        AuthWithGoogleRepositoryImpl(
            firebaseAuth = get<FirebaseAuth>(),
            firebaseFirestore = get<FirebaseFirestore>(),
            dataStore = get()
        )
    }
    single<DeletePurchasePhotoRepository> {
        DeletePurchasePhotoRepositoryImpl(
            contentResolver = get(),
            storage = get(),
            logger = get()
        )
    }
    single<BiometricRepository> {
        BiometricRepositoryImpl(
            cryptographyManager = get()
        )
    }
    single<CryptographyManager> {
        CryptographyManager(
            cipher = get(),
            sharedPreferences = get()
        )
    }
    single<PurchaseRepository> {
        PurchaseRepositoryImpl(get())
    }
    single<SettingRepository> {
        SettingRepositoryImpl(get())
    }
    single<PurchaseLaterRepository> {
        PurchaseLaterRepositoryImpl(get<FirebaseFirestore>())
    }
    single<SetPurchasePhotoRepository> {
        SetPurchasePhotoRepositoryImpl(get())
    }
    single<DeleteCollectionPurchaseRepository> {
        DeleteCollectionPurchaseRepositoryImpl(
            firebaseFirestore = get<FirebaseFirestore>(),
            firebaseAuth = get<FirebaseAuth>(),
            logger = get()
        )
    }
    single<PurchaseCategoryRepository> {
        PurchaseCategoryRepositoryImpl(get<FirebaseFirestore>())
    }
}
