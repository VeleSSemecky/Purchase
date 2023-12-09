package com.veles.purchase.data.room.migration

import android.icu.util.Currency
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.veles.purchase.data.room.core.createPrimaryIDKey
import java.util.Locale

const val LAST_VERSION = 3

fun migrationList() = arrayOf(
    MIGRATION_1_2,
    MIGRATION_2_3
)

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE SkuEntity ADD COLUMN SkuCurrencyCode TEXT NOT NULL DEFAULT '${
                Currency.getInstance(
                    Locale.getDefault()
                ).currencyCode
            }'"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, LAST_VERSION) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE `SkuPhotoEntity` (\n`SkuPhotoId` TEXT NOT NULL DEFAULT '${createPrimaryIDKey()}',\n`SkuPhotoUri` TEXT NOT NULL DEFAULT '',\n`SkuId` TEXT NOT NULL,\nPRIMARY KEY(`SkuPhotoId`)\n)"
        )
    }
}
