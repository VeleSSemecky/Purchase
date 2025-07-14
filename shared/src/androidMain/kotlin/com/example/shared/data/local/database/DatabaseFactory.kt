package com.example.shared.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

private lateinit var applicationContext: Context

/**
 * Initialize Android context for Room database
 * This should be called from Android Application class
 */
fun initializeDatabase(context: Context) {
    applicationContext = context.applicationContext
}

/**
 * Android-specific implementation of database builder
 */
actual fun getDatabaseBuilder(): RoomDatabase.Builder<PurchaseDatabase> {
    if (!::applicationContext.isInitialized) {
        throw IllegalStateException("Database not initialized. Call initializeDatabase() first.")
    }

    return Room.databaseBuilder<PurchaseDatabase>(
        context = applicationContext,
        name = PurchaseDatabase.DATABASE_NAME
    )
}
