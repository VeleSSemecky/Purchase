package com.veles.purchase.presentation.mvvm.purchase.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.collection.CollectionRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ViewModel for Purchase Edit/Add Screen
 *
 * Migrated from: /presentation/.../mvvm/purchase/edit/EditPurchaseViewModel.kt
 *
 * Manages:
 * - Loading existing purchase (edit mode) or creating new (add mode)
 * - Form fields: title, price, comment, checked, category
 * - Saving purchase to repository
 *
 * Phase 2.7 - Simplified version (no photos, no date picker for now)
 */
class PurchaseEditViewModel(
    private val collectionId: String,
    private val purchaseId: String, // Empty string for new purchase
    private val purchaseRepository: PurchaseRepository,
    private val collectionRepository: CollectionRepository
) : ViewModel() {

    // Progress state
    private val _flowProgress = MutableStateFlow(ProgressState.End)
    val flowProgress: StateFlow<ProgressState> = _flowProgress.asStateFlow()

    // Purchase model (internal state)
    private val _flowPurchaseModel = MutableStateFlow(PurchaseModel.EMPTY)

    // Derived state flows for UI
    val flowPurchaseName: StateFlow<String> = _flowPurchaseModel
        .map { it.text }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val flowPurchaseComment: StateFlow<String> = _flowPurchaseModel
        .map { it.count }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val flowPurchasePrice: StateFlow<String> = _flowPurchaseModel
        .map { it.price }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val flowPurchaseIsChecked: StateFlow<Boolean> = _flowPurchaseModel
        .map { it.isChecked }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val flowPurchaseCategory: StateFlow<PurchaseCategoryModel?> = _flowPurchaseModel
        .map { it.purchaseCategoryModel }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Categories for this collection
    private val _flowCategories = MutableStateFlow<List<PurchaseCategoryModel>>(emptyList())
    val flowCategories: StateFlow<List<PurchaseCategoryModel>> = _flowCategories.asStateFlow()

    // Whether this is a new purchase or editing existing
    val isNewPurchase: Boolean
        get() = purchaseId.isEmpty()

    init {
        loadPurchase()
        loadCategories()
    }

    /**
     * Load purchase if editing, otherwise start with empty
     */
    private fun loadPurchase() {
        viewModelScope.launch {
            _flowProgress.emit(ProgressState.Start)

            if (purchaseId.isNotEmpty()) {
                // Edit mode - load existing purchase
                val purchase = purchaseRepository.getPurchase(purchaseId, collectionId)
                if (purchase != null) {
                    _flowPurchaseModel.emit(purchase)
                }
            } else {
                // Add mode - start with empty purchase (with generated ID)
                @OptIn(ExperimentalUuidApi::class)
                val newPurchase = PurchaseModel(
                    createId = Uuid.random().toString(),
                    text = "",
                    count = "",
                    isChecked = false,
                    price = "",
                    userList = emptyList(),
                    listImage = emptyList(),
                    purchaseCategoryModel = null
                )
                _flowPurchaseModel.emit(newPurchase)
            }

            _flowProgress.emit(ProgressState.End)
        }
    }

    /**
     * Load categories for this collection
     */
    private fun loadCategories() {
        viewModelScope.launch {
            val collection = collectionRepository.getCollection(collectionId)
            if (collection != null) {
                _flowCategories.emit(collection.categoryModels)
            }
        }
    }

    /**
     * Update purchase title
     */
    fun onTitleChange(title: String) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(text = title) }
        }
    }

    /**
     * Update purchase price
     */
    fun onPriceChange(price: String) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(price = price) }
        }
    }

    /**
     * Update purchase comment (stored in "count" field)
     */
    fun onCommentChange(comment: String) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(count = comment) }
        }
    }

    /**
     * Toggle checked state
     */
    fun onCheckedChange(isChecked: Boolean) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(isChecked = isChecked) }
        }
    }

    /**
     * Select a category
     */
    fun onCategorySelected(category: PurchaseCategoryModel?) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(purchaseCategoryModel = category) }
        }
    }

    /**
     * Save purchase and navigate back
     * Returns true if save was successful, false if validation failed
     */
    suspend fun onSaveClicked(): Boolean {
        // Validate - title is required
        if (_flowPurchaseModel.value.text.isBlank()) {
            return false
        }

        _flowProgress.emit(ProgressState.Start)

        // Save purchase
        purchaseRepository.setPurchase(_flowPurchaseModel.value, collectionId)

        _flowProgress.emit(ProgressState.End)
        return true
    }

    /**
     * Progress state enum
     */
    enum class ProgressState {
        Start,  // Loading/Saving
        End     // Idle
    }
}