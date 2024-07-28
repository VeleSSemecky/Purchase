package com.veles.purchase.data.room.core

import java.util.Locale
import java.util.UUID

fun createPrimaryIDKey() = UUID.randomUUID().toString().uppercase(Locale.US)
