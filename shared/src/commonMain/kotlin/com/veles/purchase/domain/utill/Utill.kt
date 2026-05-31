@file:OptIn(ExperimentalUuidApi::class)

package com.veles.purchase.domain.utill

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun emptyString() = ""

fun dashString() = "-"

fun zeroString() = "0"

fun zeroInt() = 0

fun createPrimaryIDKey() = Uuid.random().toString().uppercase()

fun <T : Any> T?.default(default: T): T = this ?: default

/** KMP-safe two-decimal formatting (avoids JVM-only String.format). */
fun Double.formatAmount(): String {
    val abs = if (this < 0) -this else this
    val intPart = abs.toLong()
    val frac = kotlin.math.round((abs - intPart) * 100).toLong().coerceIn(0L, 99L)
    val fracStr = if (frac < 10) "0$frac" else "$frac"
    return if (this < 0) "-$intPart.$fracStr" else "$intPart.$fracStr"
}
