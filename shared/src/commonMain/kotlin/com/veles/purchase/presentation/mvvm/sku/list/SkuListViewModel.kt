package com.veles.purchase.presentation.mvvm.sku.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.usecase.sku.DeleteSkuUseCase
import com.veles.purchase.domain.usecase.sku.GetSkuUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for SKU List Screen
 *
 * Migrated from: SkuListViewModel.kt (Phase 6 - UseCase migration)
 *
 * Manages:
 * - Loading list of SKUs (shopping items)
 * - Deleting SKUs
 * - Search functionality
 *
 * Phase 6 - Migrated to UseCases (Clean Architecture)
 *
 * SKU = Stock Keeping Unit - represents items you buy frequently
 * with price tracking over time
 */
class SkuListViewModel(
    private val getSkuUseCase: GetSkuUseCase,
    private val deleteSkuUseCase: DeleteSkuUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SkuListUiState())
    val uiState: StateFlow<SkuListUiState> = _uiState.asStateFlow()

    init {
        loadSkus()
    }

    private fun loadSkus() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val skus = getSkuUseCase.getSkuModelList()
                _uiState.update {
                    it.copy(
                        skus = skus,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
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
                loadSkus() // Reload list after delete
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun onSkuClick(skuId: String) {
        // Navigate to edit handled by screen
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

/**
 * UI State for SKU List screen
 */
data class SkuListUiState(
    val skus: List<SkuModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
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
