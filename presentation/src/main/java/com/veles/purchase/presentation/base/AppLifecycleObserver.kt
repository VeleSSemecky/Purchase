package com.veles.purchase.presentation.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.veles.purchase.data.local.data.DataStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppLifecycleObserver : DefaultLifecycleObserver, KoinComponent {

    private val dataStore: DataStore by inject()

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        dataStore.setIsInForeground(true)
    }

    override fun onStop(owner: LifecycleOwner) {
        dataStore.setIsInForeground(false)
        super.onStop(owner)
    }
}
