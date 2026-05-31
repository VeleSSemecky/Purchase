package com.veles.purchase.presentation.mvvm.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.scanner.ScannedProduct
import com.veles.purchase.domain.usecase.scanner.ParsePriceTagUseCase
import com.veles.purchase.platform.scanner.TextRecognizer
import com.veles.purchase.presentation.model.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ScannerState {
    data object Idle : ScannerState()
    data object Processing : ScannerState()
    data class Result(val product: ScannedProduct) : ScannerState()
    data class Error(val message: String) : ScannerState()
}

class PriceScannerViewModel(
    private val textRecognizer: TextRecognizer,
    private val parsePriceTagUseCase: ParsePriceTagUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ScannerState>(ScannerState.Idle)
    val state: StateFlow<ScannerState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    fun onImageCaptured(bytes: ByteArray) {
        viewModelScope.launch {
            _state.value = ScannerState.Processing
            try {
                val lines = textRecognizer.recognizeText(bytes)
                val product = parsePriceTagUseCase(lines)
                _state.value = ScannerState.Result(product)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = ScannerState.Error(e.message ?: "Recognition failed")
                _events.emit(UiEvent.ShowError(e.message ?: "Recognition failed"))
            }
        }
    }

    fun onRetry() {
        _state.value = ScannerState.Idle
    }

    fun onConfirm() {
        val current = _state.value
        if (current is ScannerState.Result) {
            viewModelScope.launch {
                _events.emit(UiEvent.NavigateBack)
            }
        }
    }

    fun currentResult(): ScannedProduct? =
        (_state.value as? ScannerState.Result)?.product
}
