package com.veles.purchase.presentation.base.mvvm.service

import dagger.android.AndroidInjection
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseFirebaseMessagingService : DaggerFirebaseMessagingService() {

    private val serviceJob = Job()

    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

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
