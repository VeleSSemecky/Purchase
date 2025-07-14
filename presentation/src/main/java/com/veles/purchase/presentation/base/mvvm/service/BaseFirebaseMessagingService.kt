package com.veles.purchase.presentation.base.mvvm.service

import com.google.firebase.messaging.FirebaseMessagingService
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseFirebaseMessagingService : FirebaseMessagingService() {

    private val serviceJob = Job()

    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)


    protected open fun BaseFirebaseMessagingService.launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = serviceScope.launch {
        try {
            block(this)
        } catch (e: CancellationException) {
            throw e
        } catch (_: Throwable) {
        }
    }

    abstract override fun onNewToken(token: String)

    override fun onDestroy() {
        serviceJob.cancel()
        super.onDestroy()
    }
}
