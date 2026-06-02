package com.veles.purchase.platform.ai

import kotlinx.coroutines.flow.Flow

interface LocalModelDownloader {
    fun downloadModel(): Flow<GemmaDownloadState>
}
