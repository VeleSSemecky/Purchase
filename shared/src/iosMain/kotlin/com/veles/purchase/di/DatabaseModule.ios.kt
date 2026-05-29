package com.veles.purchase.di
import com.veles.purchase.data.room.AppDatabase
import com.veles.purchase.data.room.DatabaseBuilder
/**
 * iOS implementation of database creation
 */
actual fun createAppDatabase(): AppDatabase = DatabaseBuilder().build()
