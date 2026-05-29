package com.veles.purchase.di

import com.veles.purchase.data.firebase.firebaseModule
import com.veles.purchase.data.repository.repositoryModule
import org.koin.core.context.startKoin
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
 * Phase 6 Complete - using real Firebase + Room implementations + UseCases
 */
val appModules = listOf(
    mockDataModule, // Empty now, kept for backward compatibility
    platformModule,
    firebaseModule, // Phase 5.5: Firebase KMP services
    databaseModule, // Phase 6: Room database and DAOs
    repositoryModule, // Phase 6: All repositories (Firebase + Room)
    useCaseModule, // Phase 6: Use cases layer
    viewModelModule // Phase 2.2: ViewModels migrated
)

/**
 * Initialize Koin dependency injection
 * Call this once at app startup (Android Application.onCreate or iOS AppDelegate)
 */
fun initKoin() {
    startKoin {
        modules(appModules)
    }
}
