package com.veles.purchase.presentation.mvvm.purchase.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.CheckPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.DeletePurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.GetPurchasesUseCase
import com.veles.purchase.domain.usecase.purchase.SavePurchaseUseCase
import com.veles.purchase.domain.usecase.setting.GetSettingUseCase
import com.veles.purchase.presentation.model.UiEvent
import com.veles.purchase.presentation.model.sort.SortPurchase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * UI State for Purchase List Screen
 */
data class PurchaseListUiState(
    val collection: PurchaseCollectionModel = PurchaseCollectionModel.EMPTY,
    val purchases: List<PurchaseModel> = emptyList(),
    val searchText: String = "",
    val newNamePurchase: String = "",
    val sortPurchase: SortPurchase = SortPurchase.SORTING_UNCHECK,
    val settings: PurchaseSetting = PurchaseSetting(),
    val progress: ListPurchaseViewModel.ProgressState = ListPurchaseViewModel.ProgressState.End
)

/**
 * ViewModel for Purchase List Screen
 * Migrated from presentation module - original name: ListPurchaseViewModel
 */
class ListPurchaseViewModel(
    private val collectionId: String,
    private val getPurchasesUseCase: GetPurchasesUseCase,
    private val savePurchaseUseCase: SavePurchaseUseCase,
    private val checkPurchaseUseCase: CheckPurchaseUseCase,
    private val deletePurchaseUseCase: DeletePurchaseUseCase,
    private val getCollectionPurchaseUseCase: GetCollectionPurchaseUseCase,
    private val getSettingUseCase: GetSettingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PurchaseListUiState())
    val uiState: StateFlow<PurchaseListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    init {
        loadCollection()
        observePurchases()
        observeSettings()
    }

    private fun loadCollection() {
        viewModelScope.launch {
            try {
                val collection = getCollectionPurchaseUseCase(collectionId)
                if (collection != null) {
                    _uiState.update { it.copy(collection = collection) }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowError(e.message ?: "Failed to load collection"))
            }
        }
    }

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    private fun observePurchases() {
        _uiState
            .map { it.searchText }
            .distinctUntilChanged()
            .flatMapLatest { query ->
                getPurchasesUseCase(collectionId, query)
            }
            .retryWhen { e, attempt ->
                if (e is CancellationException) throw e
                _events.emit(UiEvent.ShowError(e.message ?: "Failed to load purchases"))
                delay(3000L * (attempt + 1).coerceAtMost(3))
                true
            }
            .onEach { purchases ->
                _uiState.update { it.copy(purchases = purchases) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSettings() {
        getSettingUseCase()
            .retryWhen { e, attempt ->
                if (e is CancellationException) throw e
                _events.emit(UiEvent.ShowError(e.message ?: "Failed to load settings"))
                delay(3000L * (attempt + 1).coerceAtMost(3))
                true
            }
            .onEach { settings ->
                _uiState.update { it.copy(settings = settings) }
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchText(text: String) {
        _uiState.update { it.copy(searchText = text) }
    }

    fun onNewNamePurchaseChanged(text: String) {
        _uiState.update { it.copy(newNamePurchase = text) }
    }

    fun onChecked(purchase: PurchaseModel) {
        viewModelScope.launch {
            try {
                checkPurchaseUseCase(collectionId, purchase)
                    .onFailure { _events.emit(UiEvent.ShowError(it.message ?: "Failed to update purchase")) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowError(e.message ?: "Unexpected error"))
            }
        }
    }

    fun deletePurchase(purchase: PurchaseModel) {
        viewModelScope.launch {
            try {
                deletePurchaseUseCase(purchase, collectionId)
                    .onFailure { _events.emit(UiEvent.ShowError(it.message ?: "Failed to delete purchase")) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowError(e.message ?: "Unexpected error"))
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun insertAdd(name: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(progress = ProgressState.Start) }
            try {
                val newPurchase = PurchaseModel(
                    createId = Uuid.random().toString().uppercase(),
                    text = name,
                    count = "",
                    isChecked = false,
                    price = "",
                    userList = emptyList(),
                    listImage = emptyList(),
                    purchaseCategoryModel = null
                )
                savePurchaseUseCase(newPurchase, collectionId)
                    .onSuccess {
                        _uiState.update { it.copy(newNamePurchase = "", searchText = "", progress = ProgressState.End) }
                    }
                    .onFailure {
                        _uiState.update { it.copy(progress = ProgressState.End) }
                        _events.emit(UiEvent.ShowError(it.message ?: "Failed to add purchase"))
                    }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(progress = ProgressState.End) }
                _events.emit(UiEvent.ShowError(e.message ?: "Unexpected error"))
            }
        }
    }

    fun setSortPurchase(sortPurchase: SortPurchase) {
        _uiState.update { it.copy(sortPurchase = sortPurchase) }
    }

    enum class ProgressState {
        Start,
        End
    }
}
