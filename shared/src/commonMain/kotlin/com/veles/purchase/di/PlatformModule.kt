package com.veles.purchase.di

import com.veles.purchase.data.firebase.firebaseModule
import com.veles.purchase.data.repository.repositoryModule
import org.koin.core.module.Module

/**
 * Platform-specific dependencies module
 *
 * Provides platform-specific implementations (Android/iOS)
 * such as BiometricAuthenticator, NotificationManager, etc.
 */
expect val platformModule: Module

/**
 * All Koin modules for the application
 */
val appModules = listOf(
    mockDataModule,
    platformModule,
    firebaseModule,  // Phase 5.5: Firebase KMP services
    repositoryModule,  // Phase 5.5: Firebase KMP repositories
    viewModelModule  // Phase 2.2: ViewModels migrated
)

