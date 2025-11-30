package com.veles.purchase.presentation.mvvm.purchase.later

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.repository.collection.CollectionRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import com.veles.purchase.domain.repository.setting.SettingRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for List Later Purchases Screen
 *
 * Migrated from: ListLaterPurchaseViewModel.kt
 *
 * Manages:
 * - Loading "later" purchases for a collection
 * - Creating new later purchases
 * - Checking/unchecking purchases
 * - Deleting purchases
 * - Searching and sorting
 *
 * Phase 2.12 - List Later screen migration
 *
 * Note: In the original app, "later" purchases are items the user wants to buy
 * in the future but not urgently. For the mock implementation, we show all
 * unchecked purchases as "later" items.
 */
class ListLaterViewModel(
    private val collectionId: String,
    private val purchaseRepository: PurchaseRepository,
    private val collectionRepository: CollectionRepository,
    private val settingRepository: SettingRepository
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
            val collection = collectionRepository.getCollection(collectionId)
            if (collection != null) {
                _uiState.update { it.copy(collection = collection) }
            }
        }
    }

    private fun loadPurchases() {
        viewModelScope.launch {
            purchaseRepository.getPurchaseFlow(collectionId).collect { purchases ->
                // Show all unchecked purchases as "later" items
                val laterPurchases = purchases.filter { !it.isChecked }
                _uiState.update { it.copy(purchases = laterPurchases) }
            }
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            settingRepository.getFlowSettingsPurchase().collect { setting ->
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
            val updated = purchase.copy(isChecked = !purchase.isChecked)
            purchaseRepository.setPurchase(updated, collectionId)
        }
    }

    fun onDeletePurchase(purchase: PurchaseModel) {
        viewModelScope.launch {
            purchaseRepository.deletePurchase(purchase.createId, collectionId)
        }
    }

    fun onCreatePurchase(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            val newPurchase = PurchaseModel(
                createId = "",
                text = name.trim(),
                count = "1",
                isChecked = false,
                price = "0",
                userList = emptyList(),
                listImage = emptyList(),
                purchaseCategoryModel = null
            )
            purchaseRepository.setPurchase(newPurchase, collectionId)
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