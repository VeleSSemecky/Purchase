package com.veles.purchase.domain.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface AppCoroutineDispatcher {
    fun coroutineDispatcherIO(): CoroutineDispatcher
    fun coroutineDispatcherMain(): CoroutineDispatcher
}
