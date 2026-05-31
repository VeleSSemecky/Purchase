package com.veles.purchase.platform.ai

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/** iOS stub — Gemma via Core ML / Foundation Models planned for future. */
class IosGemmaModelRepository : GemmaModelRepository {
    override fun isModelDownloaded(): Boolean = false
    override fun downloadModel(hfToken: String): Flow<GemmaDownloadState> =
        flowOf(GemmaDownloadState.Failed("Not supported on iOS yet"))
    override fun deleteModel() = Unit
    override fun savedToken(): String = ""
    override fun saveToken(token: String) = Unit
}
