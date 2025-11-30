package com.veles.purchase.di

import org.koin.core.module.Module
import org.koin.dsl.module

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
    viewModelModule  // Phase 2.2: ViewModels migrated
)

