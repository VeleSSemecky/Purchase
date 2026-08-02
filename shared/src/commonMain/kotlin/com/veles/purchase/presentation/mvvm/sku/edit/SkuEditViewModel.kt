@file:OptIn(ExperimentalTime::class)

package com.veles.purchase.presentation.mvvm.sku.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.ExpenseCategory
import com.veles.purchase.domain.model.SkuItemModel
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.model.scanner.PendingReceiptStore
import com.veles.purchase.domain.usecase.sku.GetSkuUseCase
import com.veles.purchase.domain.usecase.sku.SetSkuUseCase
import com.veles.purchase.domain.utill.createPrimaryIDKey
import com.veles.purchase.domain.utill.formatAmount
import com.veles.purchase.presentation.model.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class SkuEditViewModel(
    private val skuId: String?,
    private val getSkuUseCase: GetSkuUseCase,
    private val setSkuUseCase: SetSkuUseCase,
    private val pendingReceiptStore: PendingReceiptStore? = null,
    prefillName: String? = null,
    prefillPrice: String? = null,
    prefillCategory: String? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SkuEditUiState(
            skuName = prefillName ?: "",
            skuPrice = prefillPrice ?: "",
            category = prefillCategory?.let { cat ->
                ExpenseCategory.entries.firstOrNull { it.name == cat }
            } ?: ExpenseCategory.OTHER
        )
    )
    val uiState: StateFlow<SkuEditUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    init {
        if (!skuId.isNullOrEmpty()) {
            loadSku(skuId)
        } else {
            consumePendingReceipt()
        }
    }

    /**
     * Pre-fills the form from a scanned receipt handed off by the scanner.
     * Sets: total price, currency, list of product items, and receipt image.
     */
    private fun consumePendingReceipt() {
        val payload = pendingReceiptStore?.consume() ?: return
        val receipt = payload.receipt
        val itemsSum = receipt.items.sumOf { it.price }
        val price = receipt.totalAmount ?: itemsSum.takeIf { it > 0.0 }
        _uiState.update { state ->
            state.copy(
                skuPrice = price?.formatAmount() ?: state.skuPrice,
                skuCurrencyCode = receipt.currency.ifBlank { state.skuCurrencyCode },
                receiptImageBytes = payload.imageBytes,
                items = receipt.items.map {
                    SkuItemModel(
                        name = it.name,
                        quantity = it.quantity,
                        price = it.price.formatAmount(),
                        taxRate = it.taxRate
                    )
                }
            )
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
                            category = sku.category,
                            date = sku.skuLocalData,
                            items = sku.items,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.emit(UiEvent.ShowError("Expense not found"))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _events.emit(UiEvent.ShowError(e.message ?: "Failed to load expense"))
            }
        }
    }

    fun onNameChanged(name: String) {
        _uiState.update {
            it.copy(skuName = name, nameError = if (name.isBlank()) "Name is required" else null)
        }
    }

    fun onPriceChanged(price: String) {
        // Accept both dot and comma as decimal separator; normalise to dot internally
        val normalised = price.replace(',', '.')
        if (normalised.isEmpty() || normalised.matches(Regex("[0-9]+(\\.[0-9]{0,2})?"))) {
            _uiState.update {
                it.copy(skuPrice = normalised, priceError = if (normalised.isBlank()) "Price is required" else null)
            }
        }
    }

    fun onCommentChanged(comment: String) {
        _uiState.update { it.copy(skuComment = comment) }
    }

    fun onCurrencyChanged(currencyCode: String) {
        _uiState.update { it.copy(skuCurrencyCode = currencyCode) }
    }

    fun onCategoryChanged(category: ExpenseCategory) {
        _uiState.update { it.copy(category = category) }
    }

    fun onDateChanged(date: LocalDateTime) {
        _uiState.update { it.copy(date = date) }
    }

    fun onShowCurrencyPicker() {
        _uiState.update { it.copy(showCurrencyPicker = true) }
    }

    fun onDismissCurrencyPicker() {
        _uiState.update { it.copy(showCurrencyPicker = false) }
    }

    // ── Line items (products) ─────────────────────────────────────────────────

    fun onAddItem() {
        _uiState.update { it.copy(items = it.items + SkuItemModel()) }
    }

    fun onRemoveItem(skuItemId: String) {
        _uiState.update { state ->
            state.copy(items = state.items.filterNot { it.skuItemId == skuItemId })
        }
    }

    fun onItemNameChanged(skuItemId: String, name: String) =
        updateItem(skuItemId) { it.copy(name = name) }

    fun onItemQuantityChanged(skuItemId: String, quantity: String) =
        updateItem(skuItemId) { it.copy(quantity = quantity) }

    fun onItemPriceChanged(skuItemId: String, price: String) {
        // Accept both dot and comma as decimal separator; normalise to dot internally
        val normalised = price.replace(',', '.')
        if (normalised.isEmpty() || normalised.matches(Regex("[0-9]+(\\.[0-9]{0,2})?"))) {
            updateItem(skuItemId) { it.copy(price = normalised) }
        }
    }

    fun onItemTaxChanged(skuItemId: String, taxRate: String) =
        updateItem(skuItemId) { it.copy(taxRate = taxRate) }

    private fun updateItem(skuItemId: String, transform: (SkuItemModel) -> SkuItemModel) {
        _uiState.update { state ->
            state.copy(items = state.items.map { if (it.skuItemId == skuItemId) transform(it) else it })
        }
    }

    /**
     * Calculates the sum of all product line prices and sets it as the
     * purchase total. Useful when products are scanned but the grand total was
     * not recognised or is missing.
     */
    fun calculateTotalFromItems() {
        val sum = _uiState.value.items
            .mapNotNull { it.price.toDoubleOrNull() }
            .sum()
        if (sum > 0) {
            _uiState.update { it.copy(skuPrice = sum.formatAmount()) }
        }
    }

    fun save() {
        viewModelScope.launch {
            val state = _uiState.value
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
                val resolvedSkuId = if (!skuId.isNullOrEmpty()) skuId else createPrimaryIDKey()
                val items = state.items
                    .filter { it.name.isNotBlank() || it.price.isNotBlank() }
                    .map { it.copy(skuId = resolvedSkuId) }
                val sku = SkuModel(
                    skuId = resolvedSkuId,
                    skuLocalData = state.date,
                    skuName = state.skuName,
                    skuPrice = state.skuPrice,
                    skuComment = state.skuComment,
                    skuCurrencyCode = state.skuCurrencyCode,
                    category = state.category,
                    items = items
                )
                setSkuUseCase(sku, emptyList())
                    .onSuccess {
                        _uiState.update { it.copy(isSaving = false) }
                        _events.emit(UiEvent.NavigateBack)
                    }
                    .onFailure {
                        _uiState.update { it.copy(isSaving = false) }
                        _events.emit(UiEvent.ShowError(it.message ?: "Failed to save"))
                    }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false) }
                _events.emit(UiEvent.ShowError(e.message ?: "Unexpected error"))
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
data class SkuEditUiState(
    val skuName: String = "",
    val skuPrice: String = "",
    val skuComment: String = "",
    val skuCurrencyCode: String = "UAH",
    val category: ExpenseCategory = ExpenseCategory.OTHER,
    val date: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val items: List<SkuItemModel> = emptyList(),
    /** Raw JPEG bytes of the scanned receipt photo (null when not from scanner). */
    val receiptImageBytes: ByteArray? = null,
    val nameError: String? = null,
    val priceError: String? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val showCurrencyPicker: Boolean = false
)

data class SkuEditParams(
    val skuId: String? = null,
    val prefillName: String? = null,
    val prefillPrice: String? = null,
    val prefillCategory: String? = null
)
