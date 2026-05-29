package com.veles.purchase.domain.core

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

/**
 * Suspend function with timeout support
 * Default timeout: 5 seconds (5000ms)
 */
suspend inline fun <T> suspendCancellableCoroutineWithTimeout(
    timeout: Long = 5000L, // 5 seconds in milliseconds
    crossinline block: suspend () -> T
): T =
    try {
        withTimeout(timeout) {
            block()
        }
    } catch (e: TimeoutCancellationException) {
        throw e
    }
