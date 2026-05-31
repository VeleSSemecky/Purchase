package com.veles.purchase.platform.ai

/**
 * Configuration for the on-device Gemma 3 1B model.
 *
 * Model: google/gemma-3 TfLite gemma3-1b-it-int4 (~1.05 GB)
 * Source: https://www.kaggle.com/models/google/gemma-3/TfLite/gemma3-1b-it-int4
 * Requires Kaggle account + accepting Gemma licence on the model page.
 * Auth token: KAGGLE_API_TOKEN (KGAT_...)
 */
object GemmaModelConfig {
    const val MODEL_FILENAME = "gemma3-1b-it-int4.task"
    const val MODEL_SIZE_BYTES = 1_104_632_971L // ~1.05 GB (Kaggle uncompressed)

    const val KAGGLE_DOWNLOAD_URL =
        "https://www.kaggle.com/api/v1/models/google/gemma-3/TfLite/gemma3-1b-it-int4/1/download"

    // Kaggle API token — personal app, token pre-bundled for convenience
    const val KAGGLE_TOKEN = "KGAT_99e3d1f65f87279bf0c81b7c2fece8fb"

    const val TEMPERATURE = 0.2f
    const val MAX_TOKENS = 512
}

sealed class GemmaDownloadState {
    data object NotDownloaded : GemmaDownloadState()
    data class Downloading(val progressPercent: Int) : GemmaDownloadState()
    data object Downloaded : GemmaDownloadState()
    data class Failed(val error: String) : GemmaDownloadState()
}
