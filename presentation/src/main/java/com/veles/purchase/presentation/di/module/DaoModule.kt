package com.veles.purchase.presentation.di.module

import com.veles.purchase.data.room.AppDatabase
import com.veles.purchase.data.room.dao.PurchaseDAO
import com.veles.purchase.data.room.dao.SkuDAO
import com.veles.purchase.data.room.dao.SkuPhotoDAO
import org.koin.dsl.module

/**
 * Koin module for DAO dependencies
 * Converted from Dagger DaoModule
 */
val daoModule = module {

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
