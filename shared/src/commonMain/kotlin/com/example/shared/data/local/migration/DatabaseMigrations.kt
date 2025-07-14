package com.example.shared.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Migration strategy for Room KMP
 * This class handles database schema migrations across versions
 */
object DatabaseMigrations {

    /**
     * Migration from version 1 to 2 (example)
     * Add new columns or modify existing schema
     */
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Example: Add new column to existing table
            database.execSQL("ALTER TABLE purchase_table ADD COLUMN category TEXT DEFAULT '' NOT NULL")
        }
    }

    /**
     * Migration from version 2 to 3 (example)
     * Handle more complex schema changes
     */
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Example: Create new table and migrate data
            database.execSQL("""
                CREATE TABLE IF NOT EXISTS purchase_categories (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    color TEXT NOT NULL
                )
            """)
        }
    }

    /**
     * Get all available migrations
     */
    fun getAllMigrations(): Array<Migration> {
        return arrayOf(
            MIGRATION_1_2,
            MIGRATION_2_3
        )
    }
}

/**
 * Data migration utility for moving from old Room to KMP Room
 */
class DataMigrationHelper {

    companion object {
        /**
         * Migrate data from old Android Room database to new KMP Room database
         * This should be called once during app upgrade
         */
        suspend fun migrateFromOldDatabase(
            oldDatabase: Any, // Reference to old Room database
            newRepository: com.example.shared.domain.repository.PurchaseRepository
        ) {
            // Implementation will depend on your specific old database structure
            // Example pseudo-code:
            /*
            val oldPurchases = oldDatabase.purchaseDao().getAllPurchases()
            val newPurchases = oldPurchases.map { oldPurchase ->
                PurchaseModel(
                    id = oldPurchase.id,
                    text = oldPurchase.text,
                    count = oldPurchase.count,
                    isChecked = oldPurchase.check,
                    time = oldPurchase.time,
                    price = oldPurchase.price,
                    collectionId = oldPurchase.collectionId
                )
            }
            newRepository.insertPurchases(newPurchases)
            */
        }
    }
}
