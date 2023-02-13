package com.veles.purchase.presentation.di.module

import com.veles.purchase.data.room.AppDatabase
import com.veles.purchase.data.room.dao.PurchaseDAO
import com.veles.purchase.data.room.dao.SkuDAO
import com.veles.purchase.data.room.dao.SkuPhotoDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DaoModule {

    @Provides
    @Singleton
    fun provideMessageDAO(database: AppDatabase): PurchaseDAO {
        return database.getPurchaseDAO()
    }

    @Provides
    @Singleton
    fun provideSkuDAO(database: AppDatabase): SkuDAO {
        return database.getSkuDAO()
    }

    @Provides
    @Singleton
    fun provideSkuPhotoDAO(database: AppDatabase): SkuPhotoDAO {
        return database.getSkuPhotoDAO()
    }
}
