package com.veles.purchase.di

import com.veles.purchase.data.room.AppDatabase
import com.veles.purchase.data.room.dao.PurchaseDAO
import com.veles.purchase.data.room.dao.SkuDAO
import com.veles.purchase.data.room.dao.SkuPhotoDAO
import org.koin.dsl.module

/**
 * Platform-specific database creation
 */
expect fun createAppDatabase(): AppDatabase

/**
 * Database Module - provides Room database and DAOs
 */
val databaseModule = module {
    // AppDatabase - provided by platform-specific implementation
    single<AppDatabase> {
        createAppDatabase()
    }

    // DAOs
    single<PurchaseDAO> {
        get<AppDatabase>().getPurchaseDAO()
    }

    single<SkuDAO> {
        get<AppDatabase>().getSkuDAO()
    }

    single<SkuPhotoDAO> {
        get<AppDatabase>().getSkuPhotoDAO()
    }
}

