package com.veles.purchase.domain.core.dispatcher

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class AppCoroutineDispatcherImpl @Inject constructor() : AppCoroutineDispatcher {

    override fun coroutineDispatcherIO() = Dispatchers.IO

    override fun coroutineDispatcherMain() = Dispatchers.Main
}
