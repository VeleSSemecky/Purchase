package com.veles.purchase.presentation.mvvm.purchase.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.CollectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Collection List Screen
 *
 * Migrated from: /presentation/src/main/java/com/veles/purchase/presentation/presentation/mvvm/purchase/collection/list/CollectionPurchaseComposeViewModel.kt
 *
 * Manages:
 * - Loading collections from repository
 * - Deleting collections
 * - Delete confirmation dialog state
 * - Progress/loading state
 *
 * Phase 2.5 - Collection feature migration
 */
class CollectionPurchaseViewModel(
    private val collectionRepository: CollectionRepository
) : ViewModel() {

    // Progress state (Start = loading, End = loaded)
    private val _stateFlowProgress = MutableStateFlow(ProgressState.End)
    val stateFlowProgress: StateFlow<ProgressState> = _stateFlowProgress.asStateFlow()

    // List of collections
    private val _stateFlowListPurchaseCollections = MutableStateFlow<List<PurchaseCollectionModel>>(emptyList())
    val stateFlowListPurchaseCollections: StateFlow<List<PurchaseCollectionModel>> =
        _stateFlowListPurchaseCollections.asStateFlow()

    // Delete confirmation dialog state (null = closed, non-null = showing dialog for this collection)
    private val _stateFlowDeletePurchaseCollections = MutableStateFlow<PurchaseCollectionModel?>(null)
    val stateFlowDeletePurchaseCollections: StateFlow<PurchaseCollectionModel?> =
        _stateFlowDeletePurchaseCollections.asStateFlow()

    init {
        loadCollections()
    }

    /**
     * Show/hide delete confirmation dialog
     * @param purchaseCollection Collection to delete (null to close dialog)
     */
    fun onDeletePurchaseCollections(purchaseCollection: PurchaseCollectionModel?) {
        viewModelScope.launch {
            _stateFlowDeletePurchaseCollections.emit(purchaseCollection)
        }
    }

    /**
     * Delete a collection after confirmation
     * @param item Collection to delete
     */
    fun apiFirebaseRemovePurchaseCollection(item: PurchaseCollectionModel) {
        viewModelScope.launch {
            collectionRepository.deleteCollection(item)
        }
    }

    /**
     * Load collections from repository
     */
    private fun loadCollections() {
        viewModelScope.launch {
            _stateFlowProgress.emit(ProgressState.Start)
            collectionRepository.getCollections().collect { collections ->
                _stateFlowListPurchaseCollections.emit(collections)
                _stateFlowProgress.emit(ProgressState.End)
            }
        }
    }

    /**
     * Progress state enum
     */
    enum class ProgressState {
        Start,  // Loading
        End     // Loaded
    }
}