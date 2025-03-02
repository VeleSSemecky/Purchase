package com.veles.purchase.presentation.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.veles.purchase.data.repository.auth.AuthWithGoogleRepositoryImpl
import com.veles.purchase.data.repository.auth.BiometricRepositoryImpl
import com.veles.purchase.data.repository.collection.delete.DeleteCollectionPurchaseRepositoryImpl
import com.veles.purchase.data.repository.collection.get.GetCollectionPurchaseRepositoryImpl
import com.veles.purchase.data.repository.collection.set.SetCollectionPurchaseRepositoryImpl
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
import com.veles.purchase.data.repository.user.token.FirebaseMessageTokenRepositoryImpl
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import com.veles.purchase.domain.repository.auth.BiometricRepository
import com.veles.purchase.domain.repository.collection.DeleteCollectionPurchaseRepository
import com.veles.purchase.domain.repository.collection.GetCollectionPurchaseRepository
import com.veles.purchase.domain.repository.collection.SetCollectionPurchaseRepository
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.message.NotificationMessageRepository
import com.veles.purchase.domain.repository.purchase.GetPurchasePhotoRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import com.veles.purchase.domain.repository.purchase.PurchaseLaterRepository
import com.veles.purchase.domain.repository.purchase.SetPurchaseRepository
import com.veles.purchase.domain.repository.setting.SettingRepository
import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
import com.veles.purchase.domain.repository.sku.SkuRepository
import com.veles.purchase.domain.repository.storage.DeletePurchasePhotoRepository
import com.veles.purchase.domain.repository.storage.DeletePurchaseRepository
import com.veles.purchase.domain.repository.storage.SetPurchasePhotoRepository
import com.veles.purchase.domain.repository.user.FirebaseGetUserRepository
import com.veles.purchase.domain.repository.user.FirebaseMessageTokenRepository
import com.veles.purchase.domain.repository.auth.LogoutRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun provideNotificationMessageRepository(repository: NotificationMessageRepositoryImpl): NotificationMessageRepository

    @Singleton
    @Binds
    fun provideGetPurchasePhotoRepository(repository: GetPurchasePhotoRepositoryImpl): GetPurchasePhotoRepository

    @Singleton
    @Binds
    fun provideHistoryRepository(repository: HistoryRepositoryImpl): HistoryRepository

    @Singleton
    @Binds
    fun provideGetCollectionPurchaseRepository(repository: GetCollectionPurchaseRepositoryImpl): GetCollectionPurchaseRepository

    @Singleton
    @Binds
    fun provideSetCollectionPurchaseRepository(repository: SetCollectionPurchaseRepositoryImpl): SetCollectionPurchaseRepository

    @Singleton
    @Binds
    fun provideFirebaseGetUserRepository(repository: FirebaseGetUserRepositoryImpl): FirebaseGetUserRepository

    @Singleton
    @Binds
    fun provideFirebaseSetFcmTokenUserRepository(repository: FirebaseMessageTokenRepositoryImpl): FirebaseMessageTokenRepository

    @Singleton
    @Binds
    fun provideSkuPhotoRepository(repository: SkuPhotoRepositoryImpl): SkuPhotoRepository

    @Singleton
    @Binds
    fun provideSkuRepository(repository: SkuRepositoryImpl): SkuRepository

    @Singleton
    @Binds
    fun provideUserLogoutRepository(repository: LogoutRepositoryImpl): LogoutRepository

    @Singleton
    @Binds
    fun provideAuthWithGoogle(repository: AuthWithGoogleRepositoryImpl): AuthWithGoogleRepository

    @Singleton
    @Binds
    fun provideDeletePurchasePhotoRepository(repository: DeletePurchasePhotoRepositoryImpl): DeletePurchasePhotoRepository

    @Singleton
    @Binds
    fun provideBiometricRepository(repository: BiometricRepositoryImpl): BiometricRepository

    @Singleton
    @Binds
    fun providePurchaseRepository(repository: PurchaseRepositoryImpl): PurchaseRepository

    @Singleton
    @Binds
    fun provideSettingRepository(repository: SettingRepositoryImpl): SettingRepository

    @Singleton
    @Binds
    fun providePurchaseLaterRepository(repository: PurchaseLaterRepositoryImpl): PurchaseLaterRepository

    @Singleton
    @Binds
    fun provideSetPurchasePhotoRepository(repository: SetPurchasePhotoRepositoryImpl): SetPurchasePhotoRepository

    @Singleton
    @Binds
    fun provideDeleteCollectionPurchaseRepository(repository: DeleteCollectionPurchaseRepositoryImpl): DeleteCollectionPurchaseRepository

    companion object {

        @Singleton
        @Provides
        fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage

        @Singleton
        @Provides
        fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

        @Singleton
        @Provides
        fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
   }
}
