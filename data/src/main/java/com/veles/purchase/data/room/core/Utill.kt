package com.veles.purchase.data.room.core

import java.util.*

fun createPrimaryIDKey() = UUID.randomUUID().toString().uppercase(Locale.US)
