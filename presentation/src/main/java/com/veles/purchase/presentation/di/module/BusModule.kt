package com.veles.purchase.presentation.di.module

import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.data.bus.SharedFlowBusImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface BusModule {

    @Binds
    @Singleton
    fun bindStateFlowBus(flowBus: SharedFlowBusImpl): SharedFlowBus
}
