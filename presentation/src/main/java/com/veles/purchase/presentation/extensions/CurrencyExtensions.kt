package com.veles.purchase.presentation.extensions

import java.util.Currency
import java.util.Locale

fun String.toCurrency(): Currency {
    return if (isNotEmpty()) {
        Currency.getInstance(this)
    } else {
        Currency.getInstance(Locale.getDefault())
    }
}
