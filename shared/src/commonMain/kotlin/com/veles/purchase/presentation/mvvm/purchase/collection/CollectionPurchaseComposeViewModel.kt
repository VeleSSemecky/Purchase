package com.veles.purchase.presentation.mvvm.purchase.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.usecase.collection.DeletePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.FirebaseFirestorePurchaseCollectionUseCase
import com.veles.purchase.presentation.model.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
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

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

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
            try {
                deletePurchaseCollectionUseCase(item)
                    .onFailure { _events.emit(UiEvent.ShowError(it.message ?: "Failed to delete collection")) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowError(e.message ?: "Unexpected error"))
            }
        }
    }

    private fun loadCollections() {
        viewModelScope.launch {
            _stateFlowProgress.emit(ProgressState.Start)
            firebaseFirestorePurchaseCollectionUseCase()
                .retryWhen { e, attempt ->
                    if (e is CancellationException) throw e
                    _events.emit(UiEvent.ShowError(e.message ?: "Failed to load collections"))
                    delay(3000L * (attempt + 1).coerceAtMost(3))
                    true
                }
                .onEach { collections ->
                    _stateFlowListPurchaseCollections.emit(collections)
                    _stateFlowProgress.emit(ProgressState.End)
                }
                .collect()
        }
    }

    enum class ProgressState {
        Start,
        End
    }
}
