package com.veles.purchase.domain.util

/**
 * JVM/Android implementation of TimeProvider
 */
actual object TimeProvider {
    actual fun currentTimeMillis(): Long = System.currentTimeMillis()
}

