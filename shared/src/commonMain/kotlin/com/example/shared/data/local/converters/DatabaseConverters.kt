package com.example.shared.data.local.converters

import androidx.room.TypeConverter

class DatabaseConverters {

    @TypeConverter
    fun fromLong(value: Long): String {
        return value.toString()
    }

    @TypeConverter
    fun toLong(value: String): Long {
        return value.toLongOrNull() ?: 0L
    }

    @TypeConverter
    fun fromBoolean(value: Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value == 1
    }
}
