package com.veles.purchase.data.room.util

import android.net.Uri
import androidx.room.TypeConverter

object UriConverter {

    @TypeConverter
    fun fromUri(uri: Uri): String = uri.toString()

    @TypeConverter
    fun toUri(value: String): Uri = Uri.parse(value)
}
