package com.example.shared.data.local.database

import androidx.room.RoomDatabase
import com.example.shared.data.local.migration.DatabaseMigrations

/**
 * Expect function for creating Room database instance
 * Platform-specific implementations will provide actual database builder
 */
expect fun getDatabaseBuilder(): RoomDatabase.Builder<PurchaseDatabase>

/**
 * Common function to get database instance
 * Note: Migrations are handled in platform-specific implementations
 */
fun getDatabase(): PurchaseDatabase {
    return getDatabaseBuilder()
        .fallbackToDestructiveMigration(true)
        .build()
}
