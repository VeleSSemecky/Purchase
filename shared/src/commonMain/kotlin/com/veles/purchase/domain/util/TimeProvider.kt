package com.veles.purchase.domain.util

/**
 * Platform-agnostic time provider for KMP
 */
object TimeProvider {
    fun currentTimeMillis(): Long = kotlin.time.Clock.System.now().toEpochMilliseconds()
}
