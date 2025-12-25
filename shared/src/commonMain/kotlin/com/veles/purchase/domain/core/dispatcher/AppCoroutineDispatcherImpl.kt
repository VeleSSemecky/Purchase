package com.veles.purchase.domain.core.dispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class AppCoroutineDispatcherImpl : AppCoroutineDispatcher {
    override fun coroutineDispatcherIO() = Dispatchers.IO

    override fun coroutineDispatcherMain() = Dispatchers.Main
}
