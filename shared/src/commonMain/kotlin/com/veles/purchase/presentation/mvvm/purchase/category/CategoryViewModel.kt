package com.veles.purchase.presentation.mvvm.purchase.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.collection.SavePurchaseCategoryUseCase
import com.veles.purchase.presentation.model.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ViewModel for Category Management Screen
 *
 * Migrated from: CategoryViewModel.kt
 *
 * Manages:
 * - Loading categories for a collection
 * - Adding new categories
 * - Editing existing categories
 * - Deleting categories
 * - Saving changes to repository
 * - Dialog states (create, edit, confirm leave)
 *
 * Phase 2.9 - Category management feature migration
 */
class CategoryViewModel(
    private val collectionId: String,
    private val getCollectionPurchaseUseCase: GetCollectionPurchaseUseCase,
    private val savePurchaseCategoryUseCase: SavePurchaseCategoryUseCase
) : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(CategoryScreenState())
    val uiState: StateFlow<CategoryScreenState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    // Original categories (for detecting changes)
    private var originalCategories: List<PurchaseCategoryModel> = emptyList()

    init {
        loadCategories()
    }

    /**
     * Load categories from collection
     */
    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val collection = getCollectionPurchaseUseCase(collectionId)
                if (collection != null) {
                    originalCategories = collection.categoryModels
                    _uiState.update { it.copy(categories = collection.categoryModels, isLoading = false) }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _events.emit(UiEvent.ShowError(e.message ?: "Failed to load categories"))
            }
        }
    }

    /**
     * Show edit dialog for category
     */
    fun onItemClicked(position: Int, item: PurchaseCategoryModel) {
        _uiState.update { it.copy(dialogState = DialogState.EditCategoryDialog(position, item)) }
    }

    /**
     * Remove category from list
     */
    fun onRemoveCategory(item: PurchaseCategoryModel) {
        _uiState.update { currentState ->
            currentState.copy(categories = currentState.categories.filterNot { it == item })
        }
    }

    /**
     * Show create category dialog
     */
    fun onCreateCategoryDialogClicked() {
        _uiState.update { it.copy(dialogState = DialogState.CreateCategoryDialog) }
    }

    /**
     * Create new category
     */
    @OptIn(ExperimentalUuidApi::class)
    fun onCreateCategoryClicked(text: String) {
        _uiState.update { currentState ->
            val newList = currentState.categories.toMutableList().apply {
                add(PurchaseCategoryModel(id = Uuid.random().toString(), name = text))
            }
            currentState.copy(categories = newList, dialogState = DialogState.NoDialog)
        }
    }

    /**
     * Update category name
     */
    fun onTextUpdated(position: Int, text: String) {
        _uiState.update { currentState ->
            val newList = currentState.categories.toMutableList().apply {
                set(position, this[position].copy(name = text))
            }
            currentState.copy(categories = newList, dialogState = DialogState.NoDialog)
        }
    }

    /**
     * Dismiss dialog
     */
    fun onDialogDismissed() {
        _uiState.update { it.copy(dialogState = DialogState.NoDialog) }
    }

    /**
     * Check if categories have changed
     */
    fun hasChanges(): Boolean = originalCategories != _uiState.value.categories

    /**
     * Show confirm leave dialog
     */
    fun showConfirmLeaveDialog() {
        _uiState.update { it.copy(dialogState = DialogState.ConfirmLeaveDialog) }
    }

    /**
     * Save categories to collection
     */
    fun onSaveClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                val collection = getCollectionPurchaseUseCase(collectionId)
                if (collection != null) {
                    savePurchaseCategoryUseCase(collection, _uiState.value.categories)
                        .onSuccess {
                            _uiState.update { it.copy(isSaving = false) }
                            _events.emit(UiEvent.NavigateBack)
                        }
                        .onFailure {
                            _uiState.update { it.copy(isSaving = false) }
                            _events.emit(UiEvent.ShowError(it.message ?: "Failed to save categories"))
                        }
                } else {
                    _uiState.update { it.copy(isSaving = false) }
                    _events.emit(UiEvent.ShowError("Collection not found"))
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

/**
 * UI State for Category Screen
 */
data class CategoryScreenState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val categories: List<PurchaseCategoryModel> = emptyList(),
    val dialogState: DialogState = DialogState.NoDialog
)

/**
 * Dialog state sealed class
 */
sealed class DialogState {
    data object NoDialog : DialogState()
    data class EditCategoryDialog(val position: Int, val item: PurchaseCategoryModel) : DialogState()
    data object CreateCategoryDialog : DialogState()
    data object ConfirmLeaveDialog : DialogState()
}
