package com.example.shared.data.local.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSURL

/**
 * iOS-specific implementation of database builder
 */
actual fun getDatabaseBuilder(): RoomDatabase.Builder<PurchaseDatabase> {
    val dbFilePath = getDocumentsDirectory() + "/${PurchaseDatabase.DATABASE_NAME}"

    return Room.databaseBuilder<PurchaseDatabase>(
        name = dbFilePath
    )
}

/**
 * Get iOS documents directory path for database storage
 */
@OptIn(ExperimentalForeignApi::class)
private fun getDocumentsDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    return requireNotNull(documentDirectory).path!!
}
