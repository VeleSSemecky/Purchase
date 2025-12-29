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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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

    private val _flowProgress = MutableStateFlow(ProgressState.End)
    val flowProgress: StateFlow<ProgressState> = _flowProgress.asStateFlow()

    private val _flowListPurchaseModels = MutableStateFlow<List<PurchaseModel>>(emptyList())
    val flowListPurchaseModels: StateFlow<List<PurchaseModel>> = _flowListPurchaseModels.asStateFlow()

    private val _flowSearchText = MutableStateFlow("")
    val flowSearchText: StateFlow<String> = _flowSearchText.asStateFlow()

    private val _flowCollectionPurchase = MutableStateFlow(PurchaseCollectionModel.EMPTY)
    val flowCollectionPurchase: StateFlow<PurchaseCollectionModel> = _flowCollectionPurchase.asStateFlow()

    private val _flowNewNamePurchase = MutableStateFlow("")
    val flowNewNamePurchase: StateFlow<String> = _flowNewNamePurchase.asStateFlow()

    private val _flowPurchaseSetting = MutableStateFlow(PurchaseSetting())
    val flowPurchaseSetting: StateFlow<PurchaseSetting> = _flowPurchaseSetting.asStateFlow()

    private val _flowSortByChecked = MutableStateFlow(false)
    val flowSortByChecked: StateFlow<Boolean> = _flowSortByChecked.asStateFlow()

    init {
        loadCollection()
        loadPurchases()
        loadSettings()
    }

    private fun loadCollection() {
        viewModelScope.launch {
            val collection = getCollectionPurchaseUseCase(collectionId)
            if (collection != null) {
                _flowCollectionPurchase.emit(collection)
            }
        }
    }

    private fun loadPurchases() {
        viewModelScope.launch {
            _flowProgress.emit(ProgressState.Start)
            getPurchasesUseCase(collectionId, _flowSearchText.value).collect { purchases ->
                _flowListPurchaseModels.emit(purchases)
                _flowProgress.emit(ProgressState.End)
            }
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            getSettingUseCase().collect { settings ->
                _flowPurchaseSetting.emit(settings)
            }
        }
    }

    fun updateSearchText(text: String) {
        viewModelScope.launch {
            _flowSearchText.emit(text)
            loadPurchases()
        }
    }

    fun onNewNamePurchaseChanged(text: String) {
        viewModelScope.launch {
            _flowNewNamePurchase.emit(text)
        }
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
            val newPurchase = PurchaseModel(
                createId = Uuid.random().toString().uppercase(),
                text = name,
                count = "1",
                isChecked = false,
                price = "",
                userList = emptyList(),
                listImage = emptyList(),
                purchaseCategoryModel = null
            )
            savePurchaseUseCase(newPurchase, collectionId)
            _flowNewNamePurchase.emit("")
        }
    }


    fun toggleSortByChecked() {
        viewModelScope.launch {
            _flowSortByChecked.emit(!_flowSortByChecked.value)
        }
    }

    enum class ProgressState {
        Start,
        End
    }
}

