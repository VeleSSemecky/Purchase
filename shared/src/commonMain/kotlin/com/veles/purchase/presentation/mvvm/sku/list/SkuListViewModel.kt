package com.veles.purchase.presentation.mvvm.sku.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.usecase.sku.DeleteSkuUseCase
import com.veles.purchase.domain.usecase.sku.GetSkuUseCase
import com.veles.purchase.presentation.model.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for SKU List Screen
 */
class SkuListViewModel(private val getSkuUseCase: GetSkuUseCase, private val deleteSkuUseCase: DeleteSkuUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SkuListUiState())
    val uiState: StateFlow<SkuListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    init {
        loadSkus()
    }

    private fun loadSkus() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val skus = getSkuUseCase.getSkuModelList()
                _uiState.update { it.copy(skus = skus, isLoading = false) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _events.emit(UiEvent.ShowError(e.message ?: "Failed to load SKUs"))
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onDeleteSku(skuId: String) {
        viewModelScope.launch {
            try {
                deleteSkuUseCase(skuId)
                    .onSuccess { loadSkus() }
                    .onFailure { _events.emit(UiEvent.ShowError(it.message ?: "Failed to delete SKU")) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowError(e.message ?: "Unexpected error"))
            }
        }
    }

    fun onSkuClick(skuId: String) {
        // Navigate to edit handled by screen
    }
}

/**
 * UI State for SKU List screen
 */
data class SkuListUiState(
    val skus: List<SkuModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
) {
    val filteredSkus: List<SkuModel>
        get() = if (searchQuery.isBlank()) {
            skus
        } else {
            skus.filter {
                it.skuName.contains(searchQuery, ignoreCase = true) ||
                    it.skuComment.contains(searchQuery, ignoreCase = true)
            }
        }
}
