package com.veles.purchase.presentation.mvvm.sku.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.SkuSumMonthModel
import com.veles.purchase.domain.repository.sku.SkuRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for SKU Statistics Screen (Outlay Graph)
 *
 * Migrated from: OutlayGraphViewModel.kt
 *
 * Manages:
 * - Loading spending statistics by month
 * - Showing sum per SKU for selected period
 * - Year and month selection
 *
 * Phase 2.15 - SKU Statistics screen migration
 *
 * Simplified for KMP:
 * - Removed SharedFlowBus event system for year/month selection
 * - Direct year/month state management
 * - Simplified to show current year by default (2025)
 */
class SkuStatisticsViewModel(
    private val skuRepository: SkuRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SkuStatisticsUiState(
            year = 2025, // Default to current year
            month = 0 // 0 means all months
        )
    )
    val uiState: StateFlow<SkuStatisticsUiState> = _uiState.asStateFlow()

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val state = _uiState.value
                val statistics = skuRepository.getSkuSumMonthList(state.year, state.month)
                _uiState.update {
                    it.copy(
                        statistics = statistics,
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

    fun onYearChanged(year: Int) {
        _uiState.update { it.copy(year = year) }
        loadStatistics()
    }

    fun onMonthChanged(month: Int) {
        _uiState.update { it.copy(month = month) }
        loadStatistics()
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

/**
 * UI State for SKU Statistics screen
 */
data class SkuStatisticsUiState(
    val statistics: List<SkuSumMonthModel> = emptyList(),
    val year: Int,
    val month: Int = 0, // 0 means all months
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val totalSum: Double
        get() = statistics.sumOf { it.sum.toDoubleOrNull() ?: 0.0 }

    val displayPeriod: String
        get() = if (month == 0) {
            year.toString()
        } else {
            val monthName = when (month) {
                1 -> "January"
                2 -> "February"
                3 -> "March"
                4 -> "April"
                5 -> "May"
                6 -> "June"
                7 -> "July"
                8 -> "August"
                9 -> "September"
                10 -> "October"
                11 -> "November"
                12 -> "December"
                else -> "All"
            }
            "$monthName $year"
        }
}