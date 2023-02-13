package com.veles.purchase.presentation.data.bus

import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

interface SharedFlowBus {
    fun <T : Event> getSharedFlow(clazz: KClass<T>): Flow<T>
    suspend fun <T : Event> setSharedFlow(state: T)
    suspend fun <T : Event> setSharedFlowNotNull(state: T?)
}
