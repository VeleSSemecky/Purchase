package com.veles.purchase.presentation.mvvm.purchase.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.CollectionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ViewModel for Collection Edit/Add Screen
 *
 * Migrated from: EditCollectionComposeViewModel.kt
 *
 * Manages:
 * - Loading existing collection (edit mode) or creating new (add mode)
 * - Form fields: collection name
 * - Category settings navigation
 * - History navigation
 * - Saving collection to repository
 *
 * Phase 2.8 - Simplified version (no user selection, no Firebase integration)
 */
class CollectionEditViewModel(
    private val collectionId: String, // Empty string for new collection
    private val collectionRepository: CollectionRepository
) : ViewModel() {

    // Progress state
    private val _flowProgress = MutableStateFlow(ProgressState.End)
    val flowProgress: StateFlow<ProgressState> = _flowProgress.asStateFlow()

    // Collection model (internal state)
    private val _flowCollectionModel = MutableStateFlow(PurchaseCollectionModel.EMPTY)

    // Derived state flows for UI
    val flowCollectionName: StateFlow<String> = _flowCollectionModel
        .map { it.name }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val flowIsNameError: StateFlow<Boolean> = MutableStateFlow(false)

    // Whether this is a new collection or editing existing
    val isNewCollection: Boolean
        get() = collectionId.isEmpty()

    init {
        loadCollection()
    }

    /**
     * Load collection if editing, otherwise start with empty
     */
    private fun loadCollection() {
        viewModelScope.launch {
            _flowProgress.emit(ProgressState.Start)

            if (collectionId.isNotEmpty()) {
                // Edit mode - load existing collection
                val collection = collectionRepository.getCollection(collectionId)
                if (collection != null) {
                    _flowCollectionModel.emit(collection)
                }
            } else {
                // Add mode - start with empty collection (with generated ID)
                @OptIn(ExperimentalUuidApi::class)
                val newCollection = PurchaseCollectionModel(
                    id = Uuid.random().toString().uppercase(),
                    name = "",
                    creator = com.veles.purchase.domain.model.user.UserPurchaseModel.EMPTY,
                    categoryModels = emptyList(),
                    listMembers = emptyList()
                )
                _flowCollectionModel.emit(newCollection)
            }

            _flowProgress.emit(ProgressState.End)
        }
    }

    /**
     * Update collection name
     */
    fun onCollectionNameChange(name: String) {
        viewModelScope.launch {
            _flowCollectionModel.update { it.copy(name = name) }
            (flowIsNameError as MutableStateFlow).emit(name.isEmpty())
        }
    }

    /**
     * Save collection and navigate back
     * Returns true if save was successful, false if validation failed
     */
    suspend fun onSaveClicked(): Boolean {
        // Validate - name is required
        if (_flowCollectionModel.value.name.isBlank()) {
            (flowIsNameError as MutableStateFlow).emit(true)
            return false
        }

        _flowProgress.emit(ProgressState.Start)

        // Save collection
        collectionRepository.saveCollection(_flowCollectionModel.value)

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