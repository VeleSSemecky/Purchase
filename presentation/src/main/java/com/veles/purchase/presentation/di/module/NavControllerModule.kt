package com.veles.purchase.presentation.di.module

import com.veles.purchase.presentation.base.mvvm.navigation.KoinNavHostFragment
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.base.mvvm.navigation.RouterImpl
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

/**
 * Koin module for navigation dependencies
 * Converted from Dagger NavControllerModule
 */
val navControllerModule = module {

    fragment { KoinNavHostFragment() }

    single<Router> { RouterImpl() }
}
