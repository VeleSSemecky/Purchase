package com.veles.purchase.domain.core.platform

/**
 * Platform-specific currency provider
 * Returns the device's default currency code (e.g., "USD", "EUR", "GBP")
 *
 * For KMP: There's no standard library for locale/currency yet.
 * Options:
 * 1. Use expect/actual (implemented here)
 * 2. Use default "USD" and let user configure
 * 3. Use third-party library (none mature yet)
 */
expect fun getDefaultCurrencyCode(): String
