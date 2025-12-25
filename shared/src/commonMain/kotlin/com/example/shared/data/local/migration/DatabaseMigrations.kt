package com.example.shared.data.local.migration

/**
 * Database migrations stub for multiplatform compatibility
 * Note: Room migrations are Android-only. For iOS, database schema changes
 * are handled differently through CoreData or SQLite directly.
 *
 * This is a placeholder to prevent compilation errors on iOS.
 * Actual migration logic should be implemented in androidMain source set.
 */
object DatabaseMigrations {
    // Stub implementation that returns empty array
    // On Android, this will be replaced with actual Room Migration objects
    fun getAllMigrations(): Array<Any> {
        return emptyArray()
    }
}
