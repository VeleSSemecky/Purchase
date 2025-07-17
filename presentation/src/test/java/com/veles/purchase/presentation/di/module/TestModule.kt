package com.veles.purchase.presentation.di.module

import org.koin.dsl.module
import io.mockk.mockk
import com.veles.purchase.presentation.update.AppUpdateHandler
import com.veles.purchase.presentation.data.video.CameraCapability

/**
 * Test module for Koin - replaces real dependencies with mocks
 */
val testModule = module {

    // Mock AppUpdateHandler for testing
    single<AppUpdateHandler> {
        mockk<AppUpdateHandler>(relaxed = true)
    }

    // Mock CameraCapability for testing
    factory<CameraCapability> {
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
