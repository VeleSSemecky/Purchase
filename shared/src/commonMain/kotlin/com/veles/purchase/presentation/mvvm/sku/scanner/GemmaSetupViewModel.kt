package com.veles.purchase.presentation.mvvm.sku.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.platform.ai.GemmaDownloadState
import com.veles.purchase.platform.ai.GemmaModelConfig
import com.veles.purchase.platform.ai.GemmaModelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class GemmaSetupUiState {
    data object Idle : GemmaSetupUiState()           // not downloaded, no action
    data class Downloading(val percent: Int) : GemmaSetupUiState()
    data object Ready : GemmaSetupUiState()          // model is on disk
    data class Error(val message: String) : GemmaSetupUiState()
}

val GemmaModelConfig.modelSizeMb: Int get() = (MODEL_SIZE_BYTES / 1_000_000).toInt()

class GemmaSetupViewModel(
    private val modelRepo: GemmaModelRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<GemmaSetupUiState>(
        if (modelRepo.isModelDownloaded()) GemmaSetupUiState.Ready else GemmaSetupUiState.Idle
    )
    val uiState: StateFlow<GemmaSetupUiState> = _uiState.asStateFlow()

    /** Pre-filled from SharedPreferences so the user doesn't need to type it again. */
    val savedToken: String get() = modelRepo.savedToken()

    fun startDownload(token: String = GemmaModelConfig.KAGGLE_TOKEN) {
        if (_uiState.value is GemmaSetupUiState.Downloading) return
        viewModelScope.launch {
            if (token.isNotBlank()) modelRepo.saveToken(token)
            modelRepo.downloadModel(token).collect { state ->
                _uiState.value = when (state) {
                    is GemmaDownloadState.Downloading -> GemmaSetupUiState.Downloading(state.progressPercent)
                    is GemmaDownloadState.Downloaded -> GemmaSetupUiState.Ready
                    is GemmaDownloadState.Failed -> GemmaSetupUiState.Error(state.error)
                    is GemmaDownloadState.NotDownloaded -> GemmaSetupUiState.Idle
                }
            }
        }
    }

    fun deleteModel() {
        modelRepo.deleteModel()
        _uiState.value = GemmaSetupUiState.Idle
    }

    fun refresh() {
        _uiState.value = if (modelRepo.isModelDownloaded()) GemmaSetupUiState.Ready
        else GemmaSetupUiState.Idle
    }
}
