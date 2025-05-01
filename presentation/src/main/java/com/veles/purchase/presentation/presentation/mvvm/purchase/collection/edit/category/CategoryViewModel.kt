package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.usecase.collection.SavePurchaseCategoryUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.PurchaseCategoryModelUI
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.ConfirmLeaveDialogType
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.CreateCategoryDialogType
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.EditDialogType
import com.veles.purchase.presentation.model.purchase.toPurchaseCategoryModel
import com.veles.purchase.presentation.model.purchase.toPurchaseCollectionModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.CATEGORY_MODELS_KEY
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CategoryViewModel @Inject constructor(
    private val args: CategoryFragmentArgs,
    private val savePurchaseCategoryUseCase: SavePurchaseCategoryUseCase,
    private val router: Router
) : ViewModel() {

    private val _flowProgress: MutableStateFlow<Progress> = MutableStateFlow(Progress.End)
    val flowProgress: StateFlow<Progress>
        get() = _flowProgress.asStateFlow()

    private val _flowListPurchaseCategoryModel: MutableStateFlow<List<PurchaseCategoryModelUI>> =
        MutableStateFlow(args.modelCollectionPurchase.categoryModels)
    val flowListPurchaseCategoryModel: StateFlow<List<PurchaseCategoryModelUI>>
        get() = _flowListPurchaseCategoryModel.asStateFlow()

    private val _flowEditDialogType: MutableStateFlow<EditDialogType> = MutableStateFlow(EditDialogType.Close)
    val flowEditDialogType: StateFlow<EditDialogType>
        get() = _flowEditDialogType.asStateFlow()

    private val _flowCreateCategoryDialogType: MutableStateFlow<CreateCategoryDialogType> = MutableStateFlow(CreateCategoryDialogType.Close)
    val flowCreateCategoryDialogType: StateFlow<CreateCategoryDialogType>
        get() = _flowCreateCategoryDialogType.asStateFlow()

    private val _flowConfirmLeaveDialogType: MutableStateFlow<ConfirmLeaveDialogType> = MutableStateFlow(ConfirmLeaveDialogType.Close)
    val flowConfirmLeaveDialogType: StateFlow<ConfirmLeaveDialogType>
        get() = _flowConfirmLeaveDialogType.asStateFlow()

    fun onItemClicked(position: Int, item: PurchaseCategoryModelUI) = viewModelScope.launch {
        _flowEditDialogType.emit(EditDialogType.Open(position, item))
    }

    fun onRemoveCategory(item: PurchaseCategoryModelUI) = viewModelScope.launch {
        val list = flowListPurchaseCategoryModel.value.filterNot { it == item }
        _flowListPurchaseCategoryModel.emit(list)
    }

    fun onCreateCategoryDialogClicked() = viewModelScope.launch {
        _flowCreateCategoryDialogType.emit(CreateCategoryDialogType.Open)
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onCreateCategoryClicked(text: String) = viewModelScope.launch {
        val list = flowListPurchaseCategoryModel.value.toMutableList().apply {
            add(
                PurchaseCategoryModelUI(
                    id = Uuid.random().toString(),
                    name = text
                )
            )
        }
        _flowListPurchaseCategoryModel.emit(list)
        _flowCreateCategoryDialogType.emit(CreateCategoryDialogType.Close)
    }

    fun onTextUpdated(position: Int, text: String) = viewModelScope.launch {
        val list = flowListPurchaseCategoryModel.value.toMutableList().apply {
            set(position, this[position].copy(name = text))
        }
        _flowListPurchaseCategoryModel.emit(list)
        _flowEditDialogType.emit(EditDialogType.Close)
    }

    fun onEditDismissed() = viewModelScope.launch {
        _flowEditDialogType.emit(EditDialogType.Close)
    }

    fun onCreateCategoryDismiss() = viewModelScope.launch {
        _flowCreateCategoryDialogType.emit(CreateCategoryDialogType.Close)
    }

    fun onConfirmLeaveDismiss() = viewModelScope.launch {
        _flowConfirmLeaveDialogType.emit(ConfirmLeaveDialogType.Close)
    }

    fun onBackClicked() = viewModelScope.launch {
        when (args.modelCollectionPurchase.categoryModels) {
            flowListPurchaseCategoryModel.value -> router().popBackStack()
            else -> _flowConfirmLeaveDialogType.emit(ConfirmLeaveDialogType.Open)
        }
    }

    fun onConfirmLeaveClicked() {
        router().popBackStack()
    }

    fun onSaveClicked() = viewModelScope.launch {
        _flowProgress.emit(Progress.Start)
        savePurchaseCategoryUseCase(
            purchaseCollectionModel = args.modelCollectionPurchase.toPurchaseCollectionModel(),
            newPurchaseCategoryModel = flowListPurchaseCategoryModel.value.map { it.toPurchaseCategoryModel() }
        )
        router().previousBackStackEntry?.savedStateHandle?.set(CATEGORY_MODELS_KEY, flowListPurchaseCategoryModel.value)
        _flowProgress.emit(Progress.End)
        router().popBackStack()
    }
}
