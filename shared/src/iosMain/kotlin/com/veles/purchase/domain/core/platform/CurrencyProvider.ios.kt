package com.veles.purchase.domain.core.platform

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.currencyCode

/**
 * iOS implementation of currency provider
 * Uses Foundation's NSLocale to get the default currency
 */
actual fun getDefaultCurrencyCode(): String {
    return try {
        NSLocale.currentLocale.currencyCode ?: "USD"
    } catch (e: Exception) {
        "USD" // Fallback to USD if currency detection fails
    }
}

