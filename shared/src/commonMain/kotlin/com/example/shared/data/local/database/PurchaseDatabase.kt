package com.example.shared.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shared.data.local.dao.PurchaseDao
import com.example.shared.data.local.entity.PurchaseEntity
import com.example.shared.data.local.converters.DatabaseConverters

@Database(
    entities = [PurchaseEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class PurchaseDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

    companion object {
        const val DATABASE_NAME = "purchase_database.db"
    }
}
