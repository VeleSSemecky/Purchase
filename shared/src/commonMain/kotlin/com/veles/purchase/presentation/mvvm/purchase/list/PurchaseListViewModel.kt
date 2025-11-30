package com.veles.purchase.presentation.mvvm.purchase.list

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
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ViewModel for Purchase List Screen
 *
 * Migrated from: /presentation/.../mvvm/purchase/list/ListPurchaseViewModel.kt
 *
 * Manages:
 * - Loading purchases for a specific collection
 * - Adding new purchases
 * - Checking/unchecking purchases
 * - Deleting purchases
 * - Search/filter functionality
 * - Settings for purchase display
 *
 * Phase 2.6 - Purchase List feature migration (simplified version)
 */
class PurchaseListViewModel(
    private val collectionId: String,
    private val purchaseRepository: PurchaseRepository,
    private val collectionRepository: CollectionRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {

    // Progress state
    private val _flowProgress = MutableStateFlow(ProgressState.End)
    val flowProgress: StateFlow<ProgressState> = _flowProgress.asStateFlow()

    // List of purchases
    private val _flowListPurchaseModels = MutableStateFlow<List<PurchaseModel>>(emptyList())
    val flowListPurchaseModels: StateFlow<List<PurchaseModel>> = _flowListPurchaseModels.asStateFlow()

    // Search text
    private val _flowSearchText = MutableStateFlow("")
    val flowSearchText: StateFlow<String> = _flowSearchText.asStateFlow()

    // Collection info
    private val _flowCollectionPurchase = MutableStateFlow(PurchaseCollectionModel.EMPTY)
    val flowCollectionPurchase: StateFlow<PurchaseCollectionModel> = _flowCollectionPurchase.asStateFlow()

    // New purchase name input
    private val _flowNewNamePurchase = MutableStateFlow("")
    val flowNewNamePurchase: StateFlow<String> = _flowNewNamePurchase.asStateFlow()

    // Purchase settings (for display)
    private val _flowPurchaseSetting = MutableStateFlow(PurchaseSetting())
    val flowPurchaseSetting: StateFlow<PurchaseSetting> = _flowPurchaseSetting.asStateFlow()

    // Sort state (simplified - just checked/unchecked for now)
    private val _flowSortByChecked = MutableStateFlow(false)
    val flowSortByChecked: StateFlow<Boolean> = _flowSortByChecked.asStateFlow()

    init {
        loadCollection()
        loadPurchases()
        loadSettings()
    }

    /**
     * Load collection info
     */
    private fun loadCollection() {
        viewModelScope.launch {
            val collection = collectionRepository.getCollection(collectionId)
            if (collection != null) {
                _flowCollectionPurchase.emit(collection)
            }
        }
    }

    /**
     * Load purchases for this collection
     */
    private fun loadPurchases() {
        viewModelScope.launch {
            _flowProgress.emit(ProgressState.Start)
            purchaseRepository.getPurchaseFlow(collectionId).collect { purchases ->
                // Apply search filter if needed
                val filtered = if (_flowSearchText.value.isEmpty()) {
                    purchases
                } else {
                    purchases.filter {
                        it.text.contains(_flowSearchText.value, ignoreCase = true)
                    }
                }
                _flowListPurchaseModels.emit(filtered)
                _flowProgress.emit(ProgressState.End)
            }
        }
    }

    /**
     * Load purchase display settings
     */
    private fun loadSettings() {
        viewModelScope.launch {
            settingRepository.getFlowSettingsPurchase().collect { settings ->
                _flowPurchaseSetting.emit(settings)
            }
        }
    }

    /**
     * Update search text and filter purchases
     */
    fun updateSearchText(text: String) {
        viewModelScope.launch {
            _flowSearchText.emit(text)
            // Reload to apply filter
            loadPurchases()
        }
    }

    /**
     * Update new purchase name input
     */
    fun onNewNamePurchaseChanged(text: String) {
        viewModelScope.launch {
            _flowNewNamePurchase.emit(text)
        }
    }

    /**
     * Add a new purchase
     */
    @OptIn(ExperimentalUuidApi::class)
    fun insertAdd(purchaseName: String) {
        if (purchaseName.isBlank()) return

        viewModelScope.launch {
            _flowProgress.emit(ProgressState.Start)

            val newPurchase = PurchaseModel(
                createId = Uuid.random().toString(),
                text = purchaseName,
                count = "",
                isChecked = false,
                price = "",
                userList = emptyList(),
                listImage = emptyList(),
                purchaseCategoryModel = null
            )

            purchaseRepository.setPurchase(newPurchase, collectionId)

            // Clear input
            _flowNewNamePurchase.emit("")

            _flowProgress.emit(ProgressState.End)
        }
    }

    /**
     * Toggle purchase checked state
     */
    fun onChecked(purchaseModel: PurchaseModel) {
        viewModelScope.launch {
            val updated = purchaseModel.copy(isChecked = !purchaseModel.isChecked)
            purchaseRepository.setPurchase(updated, collectionId)
        }
    }

    /**
     * Delete a purchase
     */
    fun deletePurchase(purchaseModel: PurchaseModel) {
        viewModelScope.launch {
            _flowProgress.emit(ProgressState.Start)
            purchaseRepository.deletePurchase(purchaseModel.createId, collectionId)
            _flowProgress.emit(ProgressState.End)
        }
    }

    /**
     * Toggle sort by checked status
     */
    fun toggleSortByChecked() {
        viewModelScope.launch {
            _flowSortByChecked.emit(!_flowSortByChecked.value)
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