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
