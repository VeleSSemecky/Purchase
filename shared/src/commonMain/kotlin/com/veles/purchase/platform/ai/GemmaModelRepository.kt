package com.veles.purchase.platform.ai

import kotlinx.coroutines.flow.Flow

/**
 * Platform-agnostic interface for managing the Gemma 3 model file.
 * Android: downloads from Kaggle (token required once, then saved locally).
 * iOS: always returns not-downloaded (Core ML planned).
 */
interface GemmaModelRepository {
    fun isModelDownloaded(): Boolean
    fun downloadModel(hfToken: String): Flow<GemmaDownloadState>
    fun deleteModel()
    fun savedToken(): String
    fun saveToken(token: String)
}
