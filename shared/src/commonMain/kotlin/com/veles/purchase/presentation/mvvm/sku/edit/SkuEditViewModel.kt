package com.veles.purchase.presentation.mvvm.sku.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.usecase.sku.GetSkuUseCase
import com.veles.purchase.domain.usecase.sku.SetSkuUseCase
import com.veles.purchase.domain.utill.createPrimaryIDKey
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for SKU Edit Screen
 *
 * Migrated from: SkuEditViewModel.kt (Phase 6 - UseCase migration)
 *
 * Manages:
 * - Loading existing SKU for editing
 * - Creating new SKU
 * - Form validation
 * - Save operations
 *
 * Phase 6 - Migrated to UseCases (Clean Architecture)
 *
 * Simplified for KMP:
 * - Removed SharedFlowBus event system
 * - Removed SavedStateHandle navigation args
 * - Removed photo functionality (can add later)
 * - Removed currency picker (hardcoded for now)
 * - Direct validation instead of TextFieldModel wrapper
 */
class SkuEditViewModel(private val skuId: String?, private val getSkuUseCase: GetSkuUseCase, private val setSkuUseCase: SetSkuUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SkuEditUiState())
    val uiState: StateFlow<SkuEditUiState> = _uiState.asStateFlow()

    init {
        if (!skuId.isNullOrEmpty()) {
            loadSku(skuId)
        }
    }

    private fun loadSku(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val sku = getSkuUseCase.getSkuModel(id)
                if (sku != null) {
                    _uiState.update {
                        it.copy(
                            skuName = sku.skuName,
                            skuPrice = sku.skuPrice,
                            skuComment = sku.skuComment,
                            skuCurrencyCode = sku.skuCurrencyCode,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "SKU not found"
                        )
                    }
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

    fun onNameChanged(name: String) {
        _uiState.update {
            it.copy(
                skuName = name,
                nameError = if (name.isBlank()) "Name is required" else null
            )
        }
    }

    fun onPriceChanged(price: String) {
        // Validate price format (only numbers and one decimal point)
        if (price.isEmpty() || price.matches(Regex("[0-9]+(\\.[0-9]{0,2})?"))) {
            _uiState.update {
                it.copy(
                    skuPrice = price,
                    priceError = if (price.isBlank()) "Price is required" else null
                )
            }
        }
    }

    fun onCommentChanged(comment: String) {
        _uiState.update { it.copy(skuComment = comment) }
    }

    fun onCurrencyChanged(currencyCode: String) {
        _uiState.update { it.copy(skuCurrencyCode = currencyCode) }
    }

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value

            // Validate
            var hasError = false
            if (state.skuName.isBlank()) {
                _uiState.update { it.copy(nameError = "Name is required") }
                hasError = true
            }
            if (state.skuPrice.isBlank()) {
                _uiState.update { it.copy(priceError = "Price is required") }
                hasError = true
            }

            if (hasError) return@launch

            _uiState.update { it.copy(isSaving = true) }

            try {
                val sku = SkuModel(
                    skuId = skuId ?: createPrimaryIDKey(),
                    skuName = state.skuName,
                    skuPrice = state.skuPrice,
                    skuComment = state.skuComment,
                    skuCurrencyCode = state.skuCurrencyCode
                )

                setSkuUseCase(sku, emptyList()) // No photos for now
                _uiState.update { it.copy(isSaving = false) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

/**
 * UI State for SKU Edit screen
 */
data class SkuEditUiState(
    val skuName: String = "",
    val skuPrice: String = "",
    val skuComment: String = "",
    val skuCurrencyCode: String = "UAH",
    val nameError: String? = null,
    val priceError: String? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null
) {
    val isNewSku: Boolean
        get() = skuName.isEmpty() && skuPrice.isEmpty()
}
