package com.veles.purchase.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.data.room.AppDatabase
import com.veles.purchase.data.room.migration.migrationList
import com.veles.purchase.domain.core.loger.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        context: Context,
        logger: Logger
    ): AppDatabase {
        val database = context.createDB()
        try {
            database.inTransaction()
        } catch (e: IllegalStateException) {
            logger.v("AppDatabase", "AppDatabase ${e.message}", e)
            context.deleteDatabase(EnvironmentConfig.DB_KEY)
            return context.createDB()
        }
        return database
    }

    private fun Context.createDB() =
        Room.databaseBuilder(this, AppDatabase::class.java, EnvironmentConfig.DB_KEY)
            .fallbackToDestructiveMigration()
            .addMigrations(*migrationList())
            .build()
}
