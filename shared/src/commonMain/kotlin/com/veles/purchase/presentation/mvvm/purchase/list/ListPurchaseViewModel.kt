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
import com.veles.purchase.presentation.model.sort.SortPurchase
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

    init {
        loadCollection()
        observePurchases()
        observeSettings()
    }

    private fun loadCollection() {
        viewModelScope.launch {
            val collection = getCollectionPurchaseUseCase(collectionId)
            if (collection != null) {
                _uiState.update { it.copy(collection = collection) }
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
            .onEach { purchases ->
                _uiState.update { it.copy(purchases = purchases) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSettings() {
        getSettingUseCase()
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
            checkPurchaseUseCase(collectionId, purchase)
        }
    }

    fun deletePurchase(purchase: PurchaseModel) {
        viewModelScope.launch {
            deletePurchaseUseCase(purchase, collectionId)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun insertAdd(name: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(progress = ProgressState.Start) }
            
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
            
            _uiState.update { it.copy(
                newNamePurchase = "",
                searchText = "", // Reset search after adding? Optional
                progress = ProgressState.End
            ) }
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
