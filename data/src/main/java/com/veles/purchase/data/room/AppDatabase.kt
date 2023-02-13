package com.veles.purchase.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.veles.purchase.data.room.dao.PurchaseDAO
import com.veles.purchase.data.room.dao.SkuDAO
import com.veles.purchase.data.room.dao.SkuPhotoDAO
import com.veles.purchase.data.room.migration.LAST_VERSION
import com.veles.purchase.data.room.table.PurchaseTable
import com.veles.purchase.data.room.table.SkuEntity
import com.veles.purchase.data.room.table.SkuPhotoEntity
import com.veles.purchase.data.room.util.HistoryTypeConverter
import com.veles.purchase.data.room.util.LocalDateTimeConverter
import com.veles.purchase.data.room.util.UriConverter
import javax.inject.Singleton

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
    UriConverter::class,
    HistoryTypeConverter::class
)
@Singleton
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPurchaseDAO(): PurchaseDAO

    abstract fun getSkuDAO(): SkuDAO

    abstract fun getSkuPhotoDAO(): SkuPhotoDAO
}
