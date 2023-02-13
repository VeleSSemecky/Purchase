package com.veles.purchase.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> rememberFlow(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> {
    return remember(
        key1 = flow,
        key2 = lifecycleOwner
    ) {
        flow.flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
            minActiveState = minActiveState
        )
    }
}

@Composable
fun <T : R, R> Flow<T>.collectAsStateLifecycleAware(
    initial: R,
    context: CoroutineContext = EmptyCoroutineContext,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<R> {
    val lifecycleAwareFlow = rememberFlow(
        flow = this,
        minActiveState = minActiveState
    )
    return lifecycleAwareFlow.collectAsState(
        initial = initial,
        context = context
    )
}

@Composable
fun <T : R, R> StateFlow<T>.collectAsStateLifecycleAware(
    initial: R = value,
    context: CoroutineContext = EmptyCoroutineContext,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<R> {
    val lifecycleAwareFlow = rememberFlow(
        flow = this,
        minActiveState = minActiveState
    )
    return lifecycleAwareFlow.collectAsState(
        initial = initial,
        context = context
    )
}
