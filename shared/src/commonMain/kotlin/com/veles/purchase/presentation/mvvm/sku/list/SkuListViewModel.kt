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
import kotlinx.datetime.LocalDateTime

class SkuListViewModel(private val getSkuUseCase: GetSkuUseCase, private val deleteSkuUseCase: DeleteSkuUseCase) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    val uiState: StateFlow<SkuListUiState> = combine(
        getSkuUseCase.getSkuModelListFlow(),
        _searchQuery,
        _isLoading
    ) { skus, query, loading ->
        SkuListUiState(
            skus = skus.sortedByDescending { it.skuLocalData },
            searchQuery = query,
            isLoading = loading
        )
    }.catch { e ->
        _events.emit(UiEvent.ShowError(e.message ?: "Failed to load expenses"))
        emit(SkuListUiState())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SkuListUiState(isLoading = true)
    )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onDeleteSku(skuId: String) {
        viewModelScope.launch {
            try {
                deleteSkuUseCase(skuId)
                    .onFailure { _events.emit(UiEvent.ShowError(it.message ?: "Failed to delete")) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowError(e.message ?: "Unexpected error"))
            }
        }
    }
}

data class MonthGroup(
    val label: String,   // e.g. "May 2026"
    val monthSum: Double,
    val currency: String,
    val items: List<SkuModel>
)

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

    val groupedByMonth: List<MonthGroup>
        get() {
            val monthNames = listOf(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            )
            return filteredSkus
                .groupBy { sku ->
                    val d = sku.skuLocalData
                    "${d.month.ordinal + 1}-${d.year}"   // "5-2026"
                }
                .entries
                .sortedByDescending { (key, _) ->
                    val (month, year) = key.split("-").map { it.toInt() }
                    year * 100 + month
                }
                .map { (key, items) ->
                    val (month, year) = key.split("-").map { it.toInt() }
                    val label = "${monthNames[month - 1]} $year"
                    val sum = items.sumOf { it.skuPrice.toDoubleOrNull() ?: 0.0 }
                    val currency = items.firstOrNull()?.skuCurrencyCode ?: ""
                    MonthGroup(label = label, monthSum = sum, currency = currency, items = items)
                }
        }
}

