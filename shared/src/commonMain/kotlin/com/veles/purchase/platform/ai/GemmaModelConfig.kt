package com.veles.purchase.platform.ai

import com.veles.purchase.config.EnvironmentConfig

/**
 * Configuration for the on-device Gemma 3n E4B vision model.
 *
 * Model: google/gemma-3n tfLite gemma-3n-e4b-it-int4 (~3.5 GB)
 * Source: https://www.kaggle.com/models/google/gemma-3n/tfLite/gemma-3n-e4b-it-int4
 * Vision-capable — accepts images directly, no OCR pre-processing needed.
 * Requires Kaggle account + accepting Gemma licence on the model page.
 *
 * Set KAGGLE_API_KEY=<your_token> in local.properties to enable download.
 */
object GemmaModelConfig {
    const val MODEL_FILENAME = "gemma-3n-e4b-it-int4.task"
    const val MODEL_SIZE_BYTES = 3_500_000_000L // ~3.5 GB

    const val KAGGLE_DOWNLOAD_URL =
        "https://www.kaggle.com/api/v1/models/google/gemma-3n/tfLite/gemma-3n-e4b-it-int4/1/download"

    /** Kaggle credentials from local.properties — never hardcoded in source. */
    val kaggleUsername: String get() = EnvironmentConfig.KAGGLE_USERNAME
    val kaggleApiKey: String get() = EnvironmentConfig.KAGGLE_API_KEY

    const val TEMPERATURE = 0.4f
    const val TOP_K = 40
    // Vision model output is JSON ~300 tokens; allow headroom for longer receipts
    const val MAX_TOKENS = 1024
    const val MAX_IMAGES = 1
}

sealed class GemmaDownloadState {
    data object NotDownloaded : GemmaDownloadState()
    data class Downloading(val progressPercent: Int) : GemmaDownloadState()
    data object Downloaded : GemmaDownloadState()
    data class Failed(val error: String) : GemmaDownloadState()
}
