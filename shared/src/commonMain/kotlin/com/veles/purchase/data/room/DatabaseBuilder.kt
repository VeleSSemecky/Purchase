package com.veles.purchase.data.room

/**
 * Platform-specific database builder
 * Provides Room database instance for each platform
 */
expect class DatabaseBuilder {
    fun build(): AppDatabase
}

