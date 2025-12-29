package com.veles.purchase.di

import android.content.Context
import com.veles.purchase.data.room.AppDatabase
import com.veles.purchase.data.room.DatabaseBuilder
import org.koin.mp.KoinPlatform.getKoin

/**
 * Android implementation of database creation
 */
actual fun createAppDatabase(): AppDatabase {
    val context = getKoin().get<Context>()
    return DatabaseBuilder(context).build()
}

