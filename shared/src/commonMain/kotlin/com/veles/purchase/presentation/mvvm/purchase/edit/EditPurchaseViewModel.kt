package com.veles.purchase.presentation.mvvm.purchase.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.GetPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.SavePurchaseUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ViewModel for Purchase Edit/Add Screen
 * Migrated from presentation module - original name: EditPurchaseViewModel
 */
class EditPurchaseViewModel(
    private val collectionId: String,
    private val purchaseId: String,
    private val getPurchaseUseCase: GetPurchaseUseCase,
    private val savePurchaseUseCase: SavePurchaseUseCase,
    private val getCollectionPurchaseUseCase: GetCollectionPurchaseUseCase
) : ViewModel() {

    private val _flowProgress = MutableStateFlow(ProgressState.End)
    val flowProgress: StateFlow<ProgressState> = _flowProgress.asStateFlow()

    private val _flowPurchaseModel = MutableStateFlow(PurchaseModel.EMPTY)

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

    private val _flowCategories = MutableStateFlow<List<PurchaseCategoryModel>>(emptyList())
    val flowCategories: StateFlow<List<PurchaseCategoryModel>> = _flowCategories.asStateFlow()

    val isNewPurchase: Boolean
        get() = purchaseId.isEmpty()

    init {
        loadPurchase()
        loadCategories()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun loadPurchase() {
        viewModelScope.launch {
            _flowProgress.emit(ProgressState.Start)

            if (purchaseId.isNotEmpty()) {
                val purchase = getPurchaseUseCase(collectionId, purchaseId)
                if (purchase != null) {
                    _flowPurchaseModel.emit(purchase)
                }
            } else {
                val newPurchase = PurchaseModel(
                    createId = Uuid.random().toString().uppercase(),
                    text = "",
                    count = "1",
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

    private fun loadCategories() {
        viewModelScope.launch {
            val collection = getCollectionPurchaseUseCase(collectionId)
            if (collection != null) {
                _flowCategories.emit(collection.categoryModels)
            }
        }
    }

    fun onTitleChange(title: String) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(text = title) }
        }
    }

    fun onPriceChange(price: String) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(price = price) }
        }
    }

    fun onCommentChange(comment: String) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(count = comment) }
        }
    }

    fun onCheckedChange(isChecked: Boolean) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(isChecked = isChecked) }
        }
    }

    fun onCategorySelected(category: PurchaseCategoryModel?) {
        viewModelScope.launch {
            _flowPurchaseModel.update { it.copy(purchaseCategoryModel = category) }
        }
    }

    suspend fun onSaveClicked(): Boolean {
        if (_flowPurchaseModel.value.text.isBlank()) {
            return false
        }

        _flowProgress.emit(ProgressState.Start)
        savePurchaseUseCase(_flowPurchaseModel.value, collectionId)
        _flowProgress.emit(ProgressState.End)
        return true
    }

    enum class ProgressState {
        Start,
        End
    }
}

