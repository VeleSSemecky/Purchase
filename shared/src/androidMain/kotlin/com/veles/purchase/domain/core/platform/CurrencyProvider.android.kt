package com.veles.purchase.domain.core.platform

import android.icu.util.Currency
import java.util.Locale

/**
 * Android implementation of currency provider
 * Uses Android's Currency API to get the default currency
 */
actual fun getDefaultCurrencyCode(): String {
    return try {
        Currency.getInstance(Locale.getDefault()).currencyCode
    } catch (e: Exception) {
        "USD" // Fallback to USD if currency detection fails
    }
}

