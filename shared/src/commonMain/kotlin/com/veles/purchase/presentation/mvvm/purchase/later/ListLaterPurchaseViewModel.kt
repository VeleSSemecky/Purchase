package com.veles.purchase.presentation.mvvm.purchase.later

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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ViewModel for List Later Purchases Screen
 * Phase 6 - Migrated to UseCases (Clean Architecture)
 */
class ListLaterPurchaseViewModel(
    private val collectionId: String,
    private val getPurchasesUseCase: GetPurchasesUseCase,
    private val savePurchaseUseCase: SavePurchaseUseCase,
    private val checkPurchaseUseCase: CheckPurchaseUseCase,
    private val deletePurchaseUseCase: DeletePurchaseUseCase,
    private val getCollectionPurchaseUseCase: GetCollectionPurchaseUseCase,
    private val getSettingUseCase: GetSettingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListLaterUiState())
    val uiState: StateFlow<ListLaterUiState> = _uiState.asStateFlow()

    init {
        loadCollection()
        loadPurchases()
        loadSettings()
    }

    private fun loadCollection() {
        viewModelScope.launch {
            val collection = getCollectionPurchaseUseCase(collectionId)
            if (collection != null) {
                _uiState.update { it.copy(collection = collection) }
            }
        }
    }

    private fun loadPurchases() {
        viewModelScope.launch {
            getPurchasesUseCase(collectionId, "").collect { purchases ->
                // Show all unchecked purchases as "later" items
                val laterPurchases = purchases.filter { !it.isChecked }
                _uiState.update { it.copy(purchases = laterPurchases) }
            }
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            getSettingUseCase().collect { setting ->
                _uiState.update { it.copy(setting = setting) }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onPurchaseClick(purchase: PurchaseModel) {
        // Navigate to edit handled by screen
    }

    fun onPurchaseCheck(purchase: PurchaseModel) {
        viewModelScope.launch {
            checkPurchaseUseCase(collectionId, purchase)
        }
    }

    fun onDeletePurchase(purchase: PurchaseModel) {
        viewModelScope.launch {
            deletePurchaseUseCase(purchase, collectionId)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onCreatePurchase(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            val newPurchase = PurchaseModel(
                createId = Uuid.random().toString().uppercase(),
                text = name.trim(),
                count = "1",
                isChecked = false,
                price = "0",
                userList = emptyList(),
                listImage = emptyList(),
                purchaseCategoryModel = null
            )
            savePurchaseUseCase(newPurchase, collectionId)
            _uiState.update { it.copy(newPurchaseName = "") }
        }
    }

    fun onNewPurchaseNameChanged(name: String) {
        _uiState.update { it.copy(newPurchaseName = name) }
    }

    fun onClearNewPurchaseName() {
        _uiState.update { it.copy(newPurchaseName = "") }
    }
}

/**
 * UI State for List Later screen
 */
data class ListLaterUiState(
    val collection: PurchaseCollectionModel = PurchaseCollectionModel.EMPTY,
    val purchases: List<PurchaseModel> = emptyList(),
    val searchQuery: String = "",
    val newPurchaseName: String = "",
    val setting: PurchaseSetting = PurchaseSetting()
) {
    val filteredPurchases: List<PurchaseModel>
        get() = if (searchQuery.isBlank()) {
            purchases
        } else {
            purchases.filter {
                it.text.contains(searchQuery, ignoreCase = true)
            }
        }
}

