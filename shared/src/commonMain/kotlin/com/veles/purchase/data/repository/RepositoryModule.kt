package com.veles.purchase.data.repository

import com.veles.purchase.data.repository.auth.AuthWithGoogleRepositoryImpl
import com.veles.purchase.data.repository.user.FirebaseGetUserRepositoryImpl
import com.veles.purchase.data.repository.user.FirebaseMessageTokenRepositoryImpl
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import com.veles.purchase.domain.repository.user.FirebaseGetUserRepository
import com.veles.purchase.domain.repository.user.FirebaseMessageTokenRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthWithGoogleRepository> { AuthWithGoogleRepositoryImpl(
        auth = get(),
        firestore = get()
        // Optionally pass getFCMToken if needed
    ) }
    single<FirebaseGetUserRepository> { FirebaseGetUserRepositoryImpl(
        firestore = get(),
        auth = get()
    ) }
    single<FirebaseMessageTokenRepository> { FirebaseMessageTokenRepositoryImpl(
        firestore = get()
    ) }
}

