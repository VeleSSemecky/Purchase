package com.veles.purchase.data.room

import android.content.Context
import androidx.room.Room

/**
 * Android implementation of DatabaseBuilder
 * Creates Room database for Android platform
 */
actual class DatabaseBuilder(private val context: Context) {
    actual fun build(): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "purchase_database.db"
        )
            .fallbackToDestructiveMigration() // TODO: Add proper migrations
            .build()
    }
}

