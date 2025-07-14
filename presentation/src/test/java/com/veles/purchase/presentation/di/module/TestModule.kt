package com.veles.purchase.presentation.di.module

import org.koin.dsl.module
import org.mockk.mockk
import com.veles.purchase.domain.repository.PurchaseRepository
import com.veles.purchase.presentation.update.AppUpdateHandler
import com.veles.purchase.presentation.data.video.CameraCapability

/**
 * Test module for Koin - replaces real dependencies with mocks
 */
val testModule = module {

    // Mock repository for testing
    single<PurchaseRepository> {
        mockk<PurchaseRepository>(relaxed = true)
    }

    // Mock AppUpdateHandler for testing
    single {
        mockk<AppUpdateHandler>(relaxed = true)
    }

    // Mock CameraCapability for testing
    factory {
        mockk<CameraCapability>(relaxed = true)
    }
}

/**
 * Test modules list - combines test mocks with necessary real dependencies
 */
val testPresentationModules = listOf(
    testModule,
    viewModelModule  // ViewModels use mocked dependencies
)
