package com.veles.purchase.presentation.mvvm.purchase.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.usecase.collection.DeletePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.FirebaseFirestorePurchaseCollectionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Collection List Screen
 * Migrated from presentation module - original name: CollectionPurchaseComposeViewModel
 */
class CollectionPurchaseComposeViewModel(
    private val firebaseFirestorePurchaseCollectionUseCase: FirebaseFirestorePurchaseCollectionUseCase,
    private val deletePurchaseCollectionUseCase: DeletePurchaseCollectionUseCase
) : ViewModel() {

    private val _stateFlowProgress = MutableStateFlow(ProgressState.End)
    val stateFlowProgress: StateFlow<ProgressState> = _stateFlowProgress.asStateFlow()

    private val _stateFlowListPurchaseCollections = MutableStateFlow<List<PurchaseCollectionModel>>(emptyList())
    val stateFlowListPurchaseCollections: StateFlow<List<PurchaseCollectionModel>> =
        _stateFlowListPurchaseCollections.asStateFlow()

    private val _stateFlowDeletePurchaseCollections = MutableStateFlow<PurchaseCollectionModel?>(null)
    val stateFlowDeletePurchaseCollections: StateFlow<PurchaseCollectionModel?> =
        _stateFlowDeletePurchaseCollections.asStateFlow()

    init {
        loadCollections()
    }

    fun onDeletePurchaseCollections(purchaseCollection: PurchaseCollectionModel?) {
        viewModelScope.launch {
            _stateFlowDeletePurchaseCollections.emit(purchaseCollection)
        }
    }

    fun apiFirebaseRemovePurchaseCollection(item: PurchaseCollectionModel) {
        viewModelScope.launch {
            deletePurchaseCollectionUseCase(item)
        }
    }

    private fun loadCollections() {
        viewModelScope.launch {
            _stateFlowProgress.emit(ProgressState.Start)
            firebaseFirestorePurchaseCollectionUseCase().collect { collections ->
                _stateFlowListPurchaseCollections.emit(collections)
                _stateFlowProgress.emit(ProgressState.End)
            }
        }
    }

    enum class ProgressState {
        Start,
        End
    }
}

