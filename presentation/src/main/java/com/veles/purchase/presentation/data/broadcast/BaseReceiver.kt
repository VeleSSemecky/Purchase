package com.veles.purchase.presentation.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn

class CallbackBroadcastReceiver(private val callback: Callback) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        callback.onReceive(context, intent)
    }
}

fun interface Callback {
    fun onReceive(context: Context?, intent: Intent?)
}

fun callbackBroadcastReceiver(
    context: Context,
    externalScope: CoroutineScope,
    intentFilter: IntentFilter
) = callbackFlow {
    val receiver = CallbackBroadcastReceiver { _, intent ->
        trySendBlocking(intent)
    }

    ContextCompat.registerReceiver(context, receiver, intentFilter, ContextCompat.RECEIVER_NOT_EXPORTED)

    awaitClose {
        context.unregisterReceiver(receiver)
    }
}.shareIn(
    externalScope,
    replay = 1,
    started = SharingStarted.Lazily
)
