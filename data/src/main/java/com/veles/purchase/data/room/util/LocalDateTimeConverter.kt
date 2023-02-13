package com.veles.purchase.data.room.util

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

object LocalDateTimeConverter {

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): Long =
        localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    @TypeConverter
    fun toLocalDateTime(epochMilli: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault())

    fun Month.getFullLocalMonthName() = getDisplayName(
        TextStyle.FULL_STANDALONE,
        Locale.getDefault()
    ).replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}
