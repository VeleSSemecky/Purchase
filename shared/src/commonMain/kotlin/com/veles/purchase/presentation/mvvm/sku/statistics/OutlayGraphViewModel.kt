package com.veles.purchase.presentation.mvvm.sku.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.SkuSumMonthModel
import com.veles.purchase.domain.usecase.sku.GetSkuSumMontUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for SKU Statistics Screen (Outlay Graph)
 * Migrated from presentation module - original name: OutlayGraphViewModel
 */
class OutlayGraphViewModel(
    private val getSkuSumMontUseCase: GetSkuSumMontUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        OutlayGraphUiState(
            year = 2025,
            month = 0
        )
    )
    val uiState: StateFlow<OutlayGraphUiState> = _uiState.asStateFlow()

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val state = _uiState.value
                val statistics = getSkuSumMontUseCase(state.year, state.month)
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

data class OutlayGraphUiState(
    val statistics: List<SkuSumMonthModel> = emptyList(),
    val year: Int,
    val month: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val totalSum: Double
        get() = statistics.sumOf { it.skuSumMonth?.toDoubleOrNull() ?: 0.0 }

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

