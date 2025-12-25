package com.veles.purchase.domain.core

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

suspend inline fun <T> suspendCancellableCoroutineWithTimeout(
    timeout: Long = TimeUnit.SECONDS.toMillis(5.toLong()),
    crossinline block: suspend () -> T,
): T =
    try {
        withTimeout(timeout) {
            block()
        }
    } catch (e: TimeoutCancellationException) {
        throw e
    }
