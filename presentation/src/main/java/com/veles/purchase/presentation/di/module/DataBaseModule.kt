package com.veles.purchase.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.data.room.AppDatabase
import com.veles.purchase.data.room.migration.migrationList
import com.veles.purchase.domain.core.loger.Logger
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

/**
 * Koin module for database dependencies
 * Converted from Dagger DataBaseModule
 */
val databaseModule = module {

    single<AppDatabase> {
        val context = androidContext()
        val logger = get<Logger>()
        val database = context.createDB()
        try {
            database.inTransaction()
        } catch (e: IllegalStateException) {
            logger.v("AppDatabase", "AppDatabase ${e.message}", e)
            context.deleteDatabase(EnvironmentConfig.DB_KEY)
            return@single context.createDB()
        }
        database
    }
}

private fun Context.createDB() =
    Room.databaseBuilder(this, AppDatabase::class.java, EnvironmentConfig.DB_KEY)
        .fallbackToDestructiveMigration(false)
        .addMigrations(*migrationList())
        .build()
