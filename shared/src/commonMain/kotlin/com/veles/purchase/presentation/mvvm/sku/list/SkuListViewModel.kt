package com.veles.purchase.presentation.mvvm.sku.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.repository.sku.SkuRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for SKU List Screen
 *
 * Migrated from: SkuListViewModel.kt
 *
 * Manages:
 * - Loading list of SKUs (shopping items)
 * - Deleting SKUs
 * - Search functionality
 *
 * Phase 2.13 - SKU List screen migration
 *
 * SKU = Stock Keeping Unit - represents items you buy frequently
 * with price tracking over time
 */
class SkuListViewModel(
    private val skuRepository: SkuRepository
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
                val skus = skuRepository.getSkuEntityList()
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
                skuRepository.delete(skuId)
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