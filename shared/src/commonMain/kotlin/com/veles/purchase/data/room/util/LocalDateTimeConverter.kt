@file:OptIn(ExperimentalTime::class)

package com.veles.purchase.data.room.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

object LocalDateTimeConverter {

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): Long =
        localDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

    @TypeConverter
    fun toLocalDateTime(epochMilli: Long): LocalDateTime =
        Instant.fromEpochMilliseconds(epochMilli).toLocalDateTime(TimeZone.currentSystemDefault())
}

