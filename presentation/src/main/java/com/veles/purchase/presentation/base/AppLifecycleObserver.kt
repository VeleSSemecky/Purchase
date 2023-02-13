package com.veles.purchase.presentation.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.veles.purchase.data.local.data.DataStore
import javax.inject.Inject

class AppLifecycleObserver @Inject constructor(
    private val dataStore: DataStore
) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        dataStore.setIsInForeground(true)
    }

    override fun onStop(owner: LifecycleOwner) {
        dataStore.setIsInForeground(false)
        super.onStop(owner)
    }
}
