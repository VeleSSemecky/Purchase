package com.veles.purchase.presentation.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <T, R> StateFlow<T>.mapStateIn(
    scope: CoroutineScope,
    crossinline transform: (value: T) -> R
): StateFlow<R> {
    val mutableStateFlow = MutableStateFlow(transform(value))
    onEach {
        mutableStateFlow.emit(transform(it))
    }.launchIn(scope)
    return mutableStateFlow.asStateFlow()
}
