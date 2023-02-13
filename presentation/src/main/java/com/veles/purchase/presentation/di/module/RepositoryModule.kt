package com.veles.purchase.presentation.di.module

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.veles.purchase.data.repository.auth.AuthWithGoogleRepositoryImpl
import com.veles.purchase.data.repository.auth.BiometricRepositoryImpl
import com.veles.purchase.data.repository.collection.get.FirebaseCollectionPurchaseRepositoryImpl
import com.veles.purchase.data.repository.collection.set.FirebaseSetCollectionPurchaseRepositoryImpl
import com.veles.purchase.data.repository.firebase.remove.FirebaseRemoveRepositoryImpl
import com.veles.purchase.data.repository.firebase.set.FirebaseRepositoryImpl
import com.veles.purchase.data.repository.firebase.storage.FirebaseStorageRepositoryImpl
import com.veles.purchase.data.repository.history.HistoryRepositoryImpl
import com.veles.purchase.data.repository.message.NotificationMessageRepositoryImpl
import com.veles.purchase.data.repository.purchase.PurchaseRepositoryImpl
import com.veles.purchase.data.repository.sku.SkuPhotoRepositoryImpl
import com.veles.purchase.data.repository.sku.SkuRepositoryImpl
import com.veles.purchase.data.repository.storage.DeleteFirebaseStorageRepositoryImpl
import com.veles.purchase.data.repository.storage.DeleteLocalStorageRepositoryImpl
import com.veles.purchase.data.repository.user.get.FirebaseGetUserRepositoryImpl
import com.veles.purchase.data.repository.user.logout.UserLogoutRepositoryImpl
import com.veles.purchase.data.repository.user.set.FirebaseSetUserRepositoryImpl
import com.veles.purchase.data.repository.user.token.FirebaseSetFcmTokenUserRepositoryImpl
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import com.veles.purchase.domain.repository.auth.BiometricRepository
import com.veles.purchase.domain.repository.collection.get.FirebaseCollectionPurchaseRepository
import com.veles.purchase.domain.repository.collection.set.FirebaseSetCollectionPurchaseRepository
import com.veles.purchase.domain.repository.firebase.remove.FirebaseRemoveRepository
import com.veles.purchase.domain.repository.firebase.set.FirebaseRepository
import com.veles.purchase.domain.repository.firebase.storage.FirebaseStorageRepository
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.message.NotificationMessageRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import com.veles.purchase.domain.repository.sku.SkuPhotoRepository
import com.veles.purchase.domain.repository.sku.SkuRepository
import com.veles.purchase.domain.repository.storage.DeleteFirebaseStorageRepository
import com.veles.purchase.domain.repository.storage.DeleteLocalStorageRepository
import com.veles.purchase.domain.repository.user.get.FirebaseGetUserRepository
import com.veles.purchase.domain.repository.user.logout.UserLogoutRepository
import com.veles.purchase.domain.repository.user.set.FirebaseSetUserRepository
import com.veles.purchase.domain.repository.user.token.FirebaseSetFcmTokenUserRepository
import com.veles.purchase.presentation.R
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun provideFirebaseRepository(repository: FirebaseRepositoryImpl): FirebaseRepository

    @Singleton
    @Binds
    fun provideFirebaseRemoveRepository(repository: FirebaseRemoveRepositoryImpl): FirebaseRemoveRepository

    @Singleton
    @Binds
    fun provideNotificationMessageRepository(repository: NotificationMessageRepositoryImpl): NotificationMessageRepository

    @Singleton
    @Binds
    fun provideFirebaseStorageRepository(repository: FirebaseStorageRepositoryImpl): FirebaseStorageRepository

    @Singleton
    @Binds
    fun provideHistoryRepository(repository: HistoryRepositoryImpl): HistoryRepository

    @Singleton
    @Binds
    fun provideFirebaseCollectionPurchaseRepository(
        repository: FirebaseCollectionPurchaseRepositoryImpl
    ): FirebaseCollectionPurchaseRepository

    @Singleton
    @Binds
    fun provideFirebaseSetCollectionPurchaseRepository(
        repository: FirebaseSetCollectionPurchaseRepositoryImpl
    ): FirebaseSetCollectionPurchaseRepository

    @Singleton
    @Binds
    fun provideFirebaseSetUserRepository(repository: FirebaseSetUserRepositoryImpl): FirebaseSetUserRepository

    @Singleton
    @Binds
    fun provideFirebaseGetUserRepository(repository: FirebaseGetUserRepositoryImpl): FirebaseGetUserRepository

    @Singleton
    @Binds
    fun provideFirebaseSetFcmTokenUserRepository(repository: FirebaseSetFcmTokenUserRepositoryImpl): FirebaseSetFcmTokenUserRepository

    @Singleton
    @Binds
    fun provideSkuPhotoRepository(repository: SkuPhotoRepositoryImpl): SkuPhotoRepository

    @Singleton
    @Binds
    fun provideSkuRepository(repository: SkuRepositoryImpl): SkuRepository

    @Singleton
    @Binds
    fun provideUserLogoutRepository(repository: UserLogoutRepositoryImpl): UserLogoutRepository

    @Singleton
    @Binds
    fun provideAuthWithGoogle(repository: AuthWithGoogleRepositoryImpl): AuthWithGoogleRepository

    @Singleton
    @Binds
    fun provideDeleteFirebaseStorageRepository(repository: DeleteFirebaseStorageRepositoryImpl): DeleteFirebaseStorageRepository

    @Singleton
    @Binds
    fun provideDeleteLocalStorageRepository(repository: DeleteLocalStorageRepositoryImpl): DeleteLocalStorageRepository

    @Singleton
    @Binds
    fun provideBiometricRepository(repository: BiometricRepositoryImpl): BiometricRepository

    @Singleton
    @Binds
    fun providePurchaseRepository(repository: PurchaseRepositoryImpl): PurchaseRepository

    companion object {
        @Singleton
        @Provides
        fun provideFirebaseDatabase(): FirebaseDatabase {
            return Firebase.database
        }

        @Singleton
        @Provides
        fun provideFirebaseStorage(): FirebaseStorage {
            return Firebase.storage
        }

        @Singleton
        @Provides
        fun provideFirebaseFirestore(): FirebaseFirestore {
            return Firebase.firestore
        }

        @Singleton
        @Provides
        fun provideFirebaseAuth(): FirebaseAuth {
            return Firebase.auth
        }

        @Provides
        fun provideGoogleSignInClient(context: Context): GoogleSignInClient {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            return GoogleSignIn.getClient(context, gso)
        }
    }
}
