package com.veles.purchase.domain.utill

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun emptyString() = ""

fun dashString() = "-"

fun zeroString() = "0"

fun zeroInt() = 0

@OptIn(ExperimentalUuidApi::class)
fun createPrimaryIDKey() = Uuid.random().toString().uppercase()

// Simplified datetime functions for mock data
// Using a fixed timestamp for Phase 2-3 (mock data doesn't need real time)
private const val MOCK_TIMESTAMP_BASE = 1701360000000L  // Dec 1, 2023 00:00:00

fun currentLocalDateTime(): Long {
    return MOCK_TIMESTAMP_BASE
}

fun currentTimeMillis(): Long {
    return MOCK_TIMESTAMP_BASE
}

fun <T : Any> T?.default(default: T): T {
    return this ?: default
}


