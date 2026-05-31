@file:OptIn(ExperimentalTime::class)

package com.veles.purchase.presentation.mvvm.sku.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.SkuSumMonthModel
import com.veles.purchase.domain.usecase.sku.GetSkuSumMontUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class OutlayGraphViewModel(private val getSkuSumMontUseCase: GetSkuSumMontUseCase) : ViewModel() {

    private val currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year

    private val _uiState = MutableStateFlow(OutlayGraphUiState(year = currentYear))
    val uiState: StateFlow<OutlayGraphUiState> = _uiState.asStateFlow()

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val state = _uiState.value
                val statistics = getSkuSumMontUseCase(state.year, 0) // 0 = all months
                _uiState.update { it.copy(statistics = statistics, isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun onPreviousYear() {
        _uiState.update { it.copy(year = it.year - 1) }
        loadStatistics()
    }

    fun onNextYear() {
        _uiState.update { it.copy(year = it.year + 1) }
        loadStatistics()
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class MonthBarData(
    val month: Int,       // 1-12
    val label: String,    // "Jan", "Feb", …
    val sum: Double,
    val currency: String
)

data class OutlayGraphUiState(
    val statistics: List<SkuSumMonthModel> = emptyList(),
    val year: Int,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val totalSum: Double get() = statistics.sumOf { it.skuSumMonth?.toDoubleOrNull() ?: 0.0 }

    val currency: String get() = statistics.firstOrNull()?.skuCurrencyCode ?: ""

    val monthBars: List<MonthBarData>
        get() {
            val labels = listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
            val byMonth = statistics.associate { model ->
                val m = model.skuMonth?.toIntOrNull() ?: 0
                m to (model.skuSumMonth?.toDoubleOrNull() ?: 0.0)
            }
            return (1..12).map { m ->
                MonthBarData(
                    month = m,
                    label = labels[m - 1],
                    sum = byMonth[m] ?: 0.0,
                    currency = currency
                )
            }
        }
}
