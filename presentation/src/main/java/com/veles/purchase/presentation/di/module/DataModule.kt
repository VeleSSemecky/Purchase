package com.veles.purchase.presentation.di.module

import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import com.veles.purchase.data.repository.purchase.PurchaseRepositoryImpl
import com.veles.purchase.domain.repository.purchase.PurchaseRepository

/**
 * Koin module for integrating with other layers (domain, data)
 */
val dataModule = module {

    // Database
//    single<PurchaseDatabase> {
//        getDatabase()
//    }

    // Repository

    // Add more data layer dependencies here
}
