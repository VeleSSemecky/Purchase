package com.veles.purchase.domain.core.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

suspend fun <T : Any> MutableStateFlow<T>.emitNotNull(t: T?) {
    if (t != null) emit(t)
}

operator fun <T : Any> StateFlow<T>.invoke(): T = value

inline fun <reified T : Any> MutableStateFlow<T>.change(
    coroutineScope: CoroutineScope,
    crossinline changeValue: (T) -> T
) = coroutineScope.launch {
    emit(changeValue(value))
}

suspend fun <T : Any> MutableStateFlow<T>.change(
    changeValue: (T) -> T
) {
    emit(changeValue(value))
}
