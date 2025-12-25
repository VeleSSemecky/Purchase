package com.veles.purchase.domain.util

/**
 * Cross-platform time utility
 *
 * Provides current timestamp in milliseconds for all platforms
 */
expect object TimeProvider {
    fun currentTimeMillis(): Long
}

