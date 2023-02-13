package com.veles.purchase.domain.core

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout

suspend inline fun <T> suspendCancellableCoroutineWithTimeout(
    timeout: Long,
    crossinline block: (CancellableContinuation<T>) -> Unit
): T = try {
    withTimeout(timeout) {
        suspendCancellableCoroutine(block = block)
    }
} catch (e: TimeoutCancellationException) {
    throw e
}
