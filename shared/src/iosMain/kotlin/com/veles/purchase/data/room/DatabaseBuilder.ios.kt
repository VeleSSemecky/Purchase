package com.veles.purchase.data.room

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

/**
 * iOS implementation of DatabaseBuilder
 * Creates Room database for iOS platform
 */
actual class DatabaseBuilder {
    actual fun build(): AppDatabase {
        val dbFile = NSHomeDirectory() + "/purchase_database.db"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile
        )
            .setDriver(BundledSQLiteDriver())
            .fallbackToDestructiveMigration(true)
            .build()
    }
}
