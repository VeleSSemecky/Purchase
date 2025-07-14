package com.veles.purchase.presentation.data.broadcast

import android.content.Context
import android.content.IntentFilter
import kotlinx.coroutines.CoroutineScope

class RemoteActionBroadcastReceiver(
    val context: Context
) {
    private val intentFilter = IntentFilter("G_ALARM_ACTION")

    fun flowBroadcastReceiver(externalScope: CoroutineScope) = callbackBroadcastReceiver(
        externalScope = externalScope,
        context = context,
        intentFilter = intentFilter
    )
}
