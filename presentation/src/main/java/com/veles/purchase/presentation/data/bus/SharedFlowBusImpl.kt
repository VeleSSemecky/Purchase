package com.veles.purchase.presentation.data.bus

import javax.inject.Inject
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

class SharedFlowBusImpl @Inject constructor() : SharedFlowBus {

    private val sharedFlow = MutableSharedFlow<Event>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Event> getSharedFlow(clazz: KClass<T>): Flow<T> = sharedFlow.asSharedFlow()
        .filter { clazz.isInstance(it) }
        .filterNotNull() as Flow<T>

    override suspend fun <T : Event> setSharedFlow(state: T) {
        sharedFlow.emit(state)
    }

    override suspend fun <T : Event> setSharedFlowNotNull(state: T?) {
        if (state != null) sharedFlow.emit(state)
    }
}
