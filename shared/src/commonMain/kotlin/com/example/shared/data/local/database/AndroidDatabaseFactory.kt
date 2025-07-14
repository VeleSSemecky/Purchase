package com.example.shared.data.local.database

import androidx.room.RoomDatabase
import com.example.shared.data.local.migration.DatabaseMigrations

/**
 * Expect function for creating Room database instance
 * Platform-specific implementations will provide actual database builder
 */
expect fun getDatabaseBuilder(): RoomDatabase.Builder<PurchaseDatabase>

/**
 * Common function to get database instance with migrations
 */
fun getDatabase(): PurchaseDatabase {
    return getDatabaseBuilder()
        .addMigrations(*DatabaseMigrations.getAllMigrations())
        .fallbackToDestructiveMigration(true)
        .build()
}
