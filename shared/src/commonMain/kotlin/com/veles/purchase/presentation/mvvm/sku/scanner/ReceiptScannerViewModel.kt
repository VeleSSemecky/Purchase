package com.veles.purchase.presentation.mvvm.sku.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.domain.model.setting.AiEngineStrategy
import com.veles.purchase.domain.repository.setting.SettingRepository
import com.veles.purchase.platform.ai.GemmaDownloadState
import com.veles.purchase.platform.ai.LocalModelDownloader
import com.veles.purchase.platform.ai.ReceiptAiParser
import com.veles.purchase.presentation.model.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

sealed class ReceiptScannerState {
    data object Idle : ReceiptScannerState()
    data object Processing : ReceiptScannerState()
    data object EngineSelectionRequired : ReceiptScannerState()
    data class ModelDownloading(val progress: Float) : ReceiptScannerState()
    data object AiUnavailable : ReceiptScannerState()
    @Suppress("ArrayInDataClass")
    data class Result(
        val data: ReceiptData,
        val imageBytes: ByteArray = byteArrayOf()
    ) : ReceiptScannerState()
    data class Error(val message: String) : ReceiptScannerState()
}

class ReceiptScannerViewModel(
    private val settingRepository: SettingRepository
) : ViewModel(), KoinComponent {

    private val geminiNanoParser: ReceiptAiParser by inject(qualifier = named("nano"))
    private val groqCloudParser: ReceiptAiParser by inject(qualifier = named("groq"))
    private val localSlmParser: ReceiptAiParser by inject(qualifier = named("local"))
    private val ocrTextParser: ReceiptAiParser by inject(qualifier = named("ocr_text"))
    private val localModelDownloader: LocalModelDownloader by inject()

    private val _state = MutableStateFlow<ReceiptScannerState>(ReceiptScannerState.Idle)
    val state: StateFlow<ReceiptScannerState> = _state.asStateFlow()

    private val _selectedIndices = MutableStateFlow<Set<Int>>(emptySet())
    val selectedIndices: StateFlow<Set<Int>> = _selectedIndices.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    private var lastCapturedImage: ByteArray? = null

    fun onImageSelected(bytes: ByteArray) {
        lastCapturedImage = bytes
        _selectedIndices.value = emptySet()
        _state.value = ReceiptScannerState.EngineSelectionRequired
    }

    fun onImageCaptured(bytes: ByteArray) {
        lastCapturedImage = bytes
        viewModelScope.launch {
            _state.value = ReceiptScannerState.Processing
            val settings = settingRepository.getFlowSettingsPurchase().first()

            when (settings.aiEngineStrategy) {
                AiEngineStrategy.GROQ_CLOUD -> runParser(groqCloudParser, bytes)
                AiEngineStrategy.LOCAL_DOWNLOAD -> {
                    if (localSlmParser.isAvailable()) runParser(localSlmParser, bytes)
                    else _state.value = ReceiptScannerState.EngineSelectionRequired
                }
                AiEngineStrategy.OCR_TEXT_LLM -> runOcrTextParserOrRequestDownload(bytes)
                AiEngineStrategy.AUTO -> {
                    when {
                        geminiNanoParser.isAvailable() -> runParser(geminiNanoParser, bytes)
                        ocrTextParser.isAvailable() -> runParser(ocrTextParser, bytes)
                        else -> _state.value = ReceiptScannerState.EngineSelectionRequired
                    }
                }
            }
        }
    }

    fun onSelectEngine(strategy: AiEngineStrategy) {
        viewModelScope.launch {
            settingRepository.saveSettingsPurchase(
                settingRepository.getFlowSettingsPurchase().first().copy(aiEngineStrategy = strategy)
            )
            val image = lastCapturedImage ?: run {
                _state.value = ReceiptScannerState.Idle
                return@launch
            }
            when (strategy) {
                AiEngineStrategy.GROQ_CLOUD -> runParser(groqCloudParser, image)
                AiEngineStrategy.LOCAL_DOWNLOAD -> {
                    if (localSlmParser.isAvailable()) runParser(localSlmParser, image)
                    else startLocalModelDownload()
                }
                AiEngineStrategy.OCR_TEXT_LLM -> runOcrTextParserOrRequestDownload(image)
                AiEngineStrategy.AUTO -> onImageCaptured(image)
            }
        }
    }

    fun startLocalModelDownload() {
        viewModelScope.launch {
            localModelDownloader.downloadModel()
                .catch { e ->
                    _state.value = ReceiptScannerState.Error("Download failed: ${e.message}")
                }
                .collect { downloadState ->
                    when (downloadState) {
                        is GemmaDownloadState.Downloading ->
                            _state.value = ReceiptScannerState.ModelDownloading(downloadState.progressPercent / 100f)
                        is GemmaDownloadState.Downloaded -> {
                            val image = lastCapturedImage
                            if (image != null) {
                                // If we are in AUTO or OCR mode, try OCR parser after download
                                val settings = settingRepository.getFlowSettingsPurchase().first()
                                val parserToUse = if (settings.aiEngineStrategy == AiEngineStrategy.OCR_TEXT_LLM || settings.aiEngineStrategy == AiEngineStrategy.AUTO) {
                                    ocrTextParser
                                } else {
                                    localSlmParser
                                }
                                runParser(parserToUse, image)
                            } else {
                                _state.value = ReceiptScannerState.Idle
                            }
                        }
                        is GemmaDownloadState.Failed ->
                            _state.value = ReceiptScannerState.Error("Download failed: ${downloadState.error}")
                        is GemmaDownloadState.NotDownloaded -> Unit
                    }
                }
        }
    }

    private suspend fun runOcrTextParserOrRequestDownload(bytes: ByteArray) {
        if (ocrTextParser.isAvailable()) {
            runParser(ocrTextParser, bytes)
        } else {
            _state.value = ReceiptScannerState.EngineSelectionRequired
        }
    }

    private suspend fun runParser(parser: ReceiptAiParser, bytes: ByteArray) {
        try {
            _state.value = ReceiptScannerState.Processing
            val data = parser.parse(bytes) ?: run {
                _state.value = ReceiptScannerState.Error("${parser.engineType} returned no result")
                return
            }

            _selectedIndices.value = data.items.indices.toSet()
            _state.value = ReceiptScannerState.Result(data, bytes)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            _state.value = ReceiptScannerState.Error(e.message ?: "Recognition failed")
        }
    }

    fun onToggleItem(index: Int) {
        val current = _selectedIndices.value
        _selectedIndices.value = if (index in current) current - index else current + index
    }

    fun onEditItemPrice(index: Int, newPrice: Double) {
        val current = _state.value as? ReceiptScannerState.Result ?: return
        val items = current.data.items.toMutableList()
        items[index] = items[index].copy(price = newPrice)
        _state.value = current.copy(data = current.data.copy(items = items))
    }

    fun onRetry() {
        _selectedIndices.value = emptySet()
        _state.value = ReceiptScannerState.Idle
    }

    fun currentResult(): ReceiptScannerState.Result? =
        _state.value as? ReceiptScannerState.Result
}
