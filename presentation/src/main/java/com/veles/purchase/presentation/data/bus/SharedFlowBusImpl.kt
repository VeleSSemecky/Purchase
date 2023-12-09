package com.veles.purchase.presentation.data.bus

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject
import kotlin.reflect.KClass

class SharedFlowBusImpl @Inject constructor() : SharedFlowBus {

    private val sharedFlow = MutableSharedFlow<Event>()

    override fun <T : Event> getSharedFlow(clazz: KClass<T>): Flow<T> = sharedFlow.asSharedFlow()
        .filter { clazz.isInstance(it) }
        .filterIsInstance(clazz)

    override suspend fun <T : Event> setSharedFlow(state: T) {
        sharedFlow.emit(state)
    }

    override suspend fun <T : Event> setSharedFlowNotNull(state: T?) {
        if (state != null) sharedFlow.emit(state)
    }
}
