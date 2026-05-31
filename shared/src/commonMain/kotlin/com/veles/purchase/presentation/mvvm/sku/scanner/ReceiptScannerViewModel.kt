package com.veles.purchase.presentation.mvvm.sku.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.domain.usecase.scanner.ParseReceiptUseCase
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

sealed class ReceiptScannerState {
    data object Idle : ReceiptScannerState()
    data object Processing : ReceiptScannerState()
    data class Result(val data: ReceiptData, val selectedItemIndices: Set<Int> = emptySet()) : ReceiptScannerState()
    data class Error(val message: String) : ReceiptScannerState()
}

class ReceiptScannerViewModel(
    private val textRecognizer: TextRecognizer,
    private val parseReceiptUseCase: ParseReceiptUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ReceiptScannerState>(ReceiptScannerState.Idle)
    val state: StateFlow<ReceiptScannerState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    fun onImageCaptured(bytes: ByteArray) {
        viewModelScope.launch {
            _state.value = ReceiptScannerState.Processing
            try {
                val lines = textRecognizer.recognizeText(bytes)
                val data = parseReceiptUseCase(lines)
                val allIndices = data.items.indices.toSet()
                _state.value = ReceiptScannerState.Result(data, allIndices)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = ReceiptScannerState.Error(e.message ?: "Recognition failed")
                _events.emit(UiEvent.ShowError(e.message ?: "Recognition failed"))
            }
        }
    }

    fun onToggleItem(index: Int) {
        val current = _state.value as? ReceiptScannerState.Result ?: return
        val newSelected = if (index in current.selectedItemIndices) {
            current.selectedItemIndices - index
        } else {
            current.selectedItemIndices + index
        }
        _state.value = current.copy(selectedItemIndices = newSelected)
    }

    fun onRetry() {
        _state.value = ReceiptScannerState.Idle
    }

    fun currentResult(): ReceiptScannerState.Result? =
        _state.value as? ReceiptScannerState.Result
}
