package com.veles.purchase.presentation.di.module

import org.koin.dsl.module

/**
 * Main Koin module that combines all other modules
 */
val presentationModule = module {
    includes(
        viewModelModule,
        uiModule,
        dataModule
    )
}

/**
 * List of all Koin modules for the presentation layer
 */
val allPresentationModules = listOf(
    presentationModule
)
