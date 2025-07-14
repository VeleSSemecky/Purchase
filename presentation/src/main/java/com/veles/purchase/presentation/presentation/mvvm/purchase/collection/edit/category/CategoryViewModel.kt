package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.usecase.collection.SavePurchaseCategoryUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.model.purchase.PurchaseCategoryModelUI
import com.veles.purchase.presentation.model.purchase.toPurchaseCategoryModel
import com.veles.purchase.presentation.model.purchase.toPurchaseCollectionModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.CATEGORY_MODELS_KEY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CategoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val savePurchaseCategoryUseCase: SavePurchaseCategoryUseCase,
    private val router: Router
) : ViewModel() {

    private val args: CategoryFragmentArgs = CategoryFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _uiState = MutableStateFlow(CategoryScreenState(categories = args.modelCollectionPurchase.categoryModels))
    val uiState: StateFlow<CategoryScreenState> = _uiState.asStateFlow()

    fun onItemClicked(position: Int, item: PurchaseCategoryModelUI) {
        _uiState.update { it.copy(dialogState = DialogState.EditCategoryDialog(position, item)) }
    }

    fun onRemoveCategory(item: PurchaseCategoryModelUI) {
        _uiState.update { currentState ->
            currentState.copy(categories = currentState.categories.filterNot { it == item })
        }
    }

    fun onCreateCategoryDialogClicked() {
        _uiState.update { it.copy(dialogState = DialogState.CreateCategoryDialog) }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onCreateCategoryClicked(text: String) {
        _uiState.update { currentState ->
            val newList = currentState.categories.toMutableList().apply {
                add(PurchaseCategoryModelUI(id = Uuid.random().toString(), name = text))
            }
            currentState.copy(categories = newList, dialogState = DialogState.NoDialog)
        }
    }

    fun onTextUpdated(position: Int, text: String) {
        _uiState.update { currentState ->
            val newList = currentState.categories.toMutableList().apply {
                set(position, this[position].copy(name = text))
            }
            currentState.copy(categories = newList, dialogState = DialogState.NoDialog)
        }
    }

    fun onDialogDismissed() {
        _uiState.update { it.copy(dialogState = DialogState.NoDialog) }
    }

    fun onBackClicked() {
        if (args.modelCollectionPurchase.categoryModels == _uiState.value.categories) {
            router().popBackStack()
        } else {
            _uiState.update { it.copy(dialogState = DialogState.ConfirmLeaveDialog) }
        }
    }

    fun onConfirmLeaveClicked() {
        router().popBackStack()
    }

    fun onSaveClicked() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        savePurchaseCategoryUseCase(
            purchaseCollectionModel = args.modelCollectionPurchase.toPurchaseCollectionModel(),
            newPurchaseCategoryModel = _uiState.value.categories.map { it.toPurchaseCategoryModel() }
        )
        router().previousBackStackEntry?.savedStateHandle?.set(CATEGORY_MODELS_KEY, _uiState.value.categories)
        _uiState.update { it.copy(isLoading = false) }
        router().popBackStack()
    }
}
