package com.veles.purchase.data.room.util

import androidx.room.TypeConverter
import com.veles.purchase.domain.model.history.HistoryType

class HistoryTypeConverter {
    @TypeConverter
    fun toHistoryType(value: String) = enumValueOf<HistoryType>(value)

    @TypeConverter
    fun fromHistoryType(value: HistoryType) = value.name
}
