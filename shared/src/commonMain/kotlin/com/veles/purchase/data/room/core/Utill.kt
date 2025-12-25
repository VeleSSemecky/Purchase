@file:OptIn(ExperimentalUuidApi::class)

package com.veles.purchase.data.room.core

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun createPrimaryIDKey() = Uuid.random().toString().uppercase()
