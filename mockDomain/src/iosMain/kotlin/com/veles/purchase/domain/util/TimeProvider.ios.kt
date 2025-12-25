package com.veles.purchase.domain.util

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

/**
 * iOS implementation of TimeProvider
 *
 * Uses NSDate to get current timestamp
 */
actual object TimeProvider {
    actual fun currentTimeMillis(): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }
}

