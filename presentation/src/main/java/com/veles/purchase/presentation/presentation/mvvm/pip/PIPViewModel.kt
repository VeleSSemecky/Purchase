package com.veles.purchase.presentation.presentation.mvvm.pip

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.presentation.data.broadcast.RemoteActionBroadcastReceiver
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PIPViewModel @Inject constructor(
    private val logger: Logger,
    remoteActionBroadcastReceiver: RemoteActionBroadcastReceiver
) : ViewModel() {

    val flowBroadcastReceiverRemoteAction: Flow<Intent?> =
        remoteActionBroadcastReceiver.flowBroadcastReceiver(viewModelScope)

    fun getLogger() = logger
}
