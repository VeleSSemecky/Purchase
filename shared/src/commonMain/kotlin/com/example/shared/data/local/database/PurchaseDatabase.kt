package com.example.shared.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.example.shared.data.local.dao.PurchaseDao
import com.example.shared.data.local.entity.PurchaseEntity
import com.example.shared.data.local.converters.DatabaseConverters
import com.veles.purchase.data.room.AppDatabase

@Database(
    entities = [PurchaseEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
@ConstructedBy(PurchaseDatabaseConstructor::class)
abstract class PurchaseDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

    companion object {
        const val DATABASE_NAME = "purchase_database.db"
    }
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object PurchaseDatabaseConstructor : RoomDatabaseConstructor<PurchaseDatabase> {
    override fun initialize(): PurchaseDatabase
}
