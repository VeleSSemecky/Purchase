package com.example.shared.di

import com.example.shared.data.local.database.PurchaseDatabase
import com.example.shared.data.local.database.getDatabase
import com.example.shared.data.repository.PurchaseRepositoryImpl
import com.example.shared.domain.repository.PurchaseRepository

/**
 * Database Module - provides database-related dependencies
 */
object DatabaseModule {

    val database: PurchaseDatabase by lazy {
        getDatabase()
    }

    val purchaseRepository: PurchaseRepository by lazy {
        PurchaseRepositoryImpl(database.purchaseDao())
    }
}

/**
 * Extension function to get database instance
 */
fun getDatabaseInstance(): PurchaseDatabase {
    return DatabaseModule.database
}
