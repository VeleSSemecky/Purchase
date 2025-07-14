package com.veles.purchase.presentation.di.module

import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.data.bus.SharedFlowBusImpl
import org.koin.dsl.module

/**
 * Koin module for bus dependencies
 * Converted from Dagger BusModule
 */
val busModule = module {

    single<SharedFlowBus> { SharedFlowBusImpl() }
}
