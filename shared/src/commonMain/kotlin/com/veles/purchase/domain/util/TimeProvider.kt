package com.veles.purchase.domain.util

/**
 * Platform-agnostic time provider for KMP
 */
object TimeProvider {
    fun currentTimeMillis(): Long {
        return kotlin.time.Clock.System.now().toEpochMilliseconds()
    }
}

