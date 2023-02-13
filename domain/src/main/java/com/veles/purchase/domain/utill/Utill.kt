package com.veles.purchase.domain.utill

import java.util.Locale
import java.util.UUID

fun emptyString() = ""

fun dashString() = "-"

fun zeroString() = "0"

fun zeroInt() = 0

fun createPrimaryIDKey() = UUID.randomUUID().toString().uppercase(Locale.US)

fun <T : Any> T?.default(default: T): T {
    return this ?: default
}
