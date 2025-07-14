package com.veles.purchase.presentation.di.module

import org.koin.dsl.module
import com.veles.purchase.presentation.update.AppUpdateHandler
import com.veles.purchase.presentation.base.AppLifecycleObserver
import com.veles.purchase.presentation.data.video.CameraCapability

/**
 * Koin module for UI-related dependencies in presentation layer
 */
val uiModule = module {

    // App Update Handler
    single {
        AppUpdateHandler(
            context = get()
        )
    }

    // App Lifecycle Observer
    single {
        AppLifecycleObserver()
    }

}
