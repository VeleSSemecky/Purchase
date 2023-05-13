package com.veles.purchase.domain.core

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

suspend inline fun <T> suspendCancellableCoroutineWithTimeout(
    timeout: Long = TimeUnit.SECONDS.toMillis(5.toLong()),
    crossinline block: suspend () -> T
): T = try {
    withTimeout(timeout) {
        block()
    }
} catch (e: TimeoutCancellationException) {
    throw e
}
