package com.veles.purchase.data.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.veles.purchase.data.room.dao.PurchaseDAO
import com.veles.purchase.data.room.dao.SkuDAO
import com.veles.purchase.data.room.dao.SkuPhotoDAO
import com.veles.purchase.data.room.table.PurchaseTable
import com.veles.purchase.data.room.table.SkuEntity
import com.veles.purchase.data.room.table.SkuPhotoEntity
import com.veles.purchase.data.room.util.HistoryTypeConverter
import com.veles.purchase.data.room.util.LocalDateTimeConverter

const val LAST_VERSION = 3

@Database(
    entities = [
        PurchaseTable::class,
        SkuEntity::class,
        SkuPhotoEntity::class
    ],
    version = LAST_VERSION,
    exportSchema = false
)
@TypeConverters(
    LocalDateTimeConverter::class,
    HistoryTypeConverter::class
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPurchaseDAO(): PurchaseDAO

    abstract fun getSkuDAO(): SkuDAO

    abstract fun getSkuPhotoDAO(): SkuPhotoDAO
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
