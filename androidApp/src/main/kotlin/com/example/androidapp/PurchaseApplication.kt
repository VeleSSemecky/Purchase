package com.example.androidapp

import android.app.Application
import com.example.shared.data.local.database.initializeDatabase

class PurchaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Room database for Android
        initializeDatabase(this)
    }
}
