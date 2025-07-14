package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.usecase.collection.FirebaseFirestorePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.SetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.user.UserUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.extensions.launchOnError
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.toPurchaseCollectionModel
import com.veles.purchase.presentation.model.purchase.toPurchaseCollectionModelUI
import com.veles.purchase.presentation.model.user.UserCheckedUI
import com.veles.purchase.presentation.model.user.toUserPurchaseModelUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val CATEGORY_MODELS_KEY = "CATEGORY_MODELS_KEY"

class EditCollectionComposeViewModel(
    savedStateHandle: SavedStateHandle,
    private val setCollectionPurchaseUseCase: SetCollectionPurchaseUseCase,
    private val firebaseFirestorePurchaseCollectionUseCase: FirebaseFirestorePurchaseCollectionUseCase,
    private val userUseCase: UserUseCase,
    private val router: Router
) : ViewModel() {

    private val args: EditCollectionComposeFragmentArgs =
        EditCollectionComposeFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _uiState = MutableStateFlow(EditCollectionScreenState.EMPTY)
    val uiState = _uiState.asStateFlow()

    init {
        apiFirebaseUser()
        router().currentBackStackEntry?.savedStateHandle?.getStateFlow(
            CATEGORY_MODELS_KEY,
            _uiState.value.purchaseCollectionModelUI.categoryModels
        )?.onEach { list ->
            _uiState.emit(
                _uiState.value.copy(
                    purchaseCollectionModelUI = _uiState.value.purchaseCollectionModelUI.copy(
                        categoryModels = list
                    )
                )
            )
        }?.launchIn(viewModelScope)
    }

    fun setCollectionName(name: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                isCollectionNameError = name.isEmpty(),
                purchaseCollectionModelUI = it.purchaseCollectionModelUI.copy(name = name),
            )
        }
    }

    fun save() = viewModelScope.launch {
        _uiState.emit(
            _uiState.value.copy(progress = Progress.Start)
        )

        if (_uiState.value.purchaseCollectionModelUI.name.isEmpty()) {
            _uiState.emit(
                _uiState.value.copy(
                    isCollectionNameError = true,
                    progress = Progress.Start
                )
            )
        }
        val purchaseCollection = _uiState.value.purchaseCollectionModelUI.toPurchaseCollectionModel()
        apiFirebaseFirestore(purchaseCollection)

        _uiState.emit(
            _uiState.value.copy(progress = Progress.End)
        )
        router().popBackStack()
    }

    private suspend fun apiFirebaseFirestore(
        purchaseModel: PurchaseCollectionModel
    ) {
        val list = _uiState.value.listUserChecked
            .filter { userChecked -> userChecked.isCheck }
            .map { userChecked -> userChecked.userPurchase.uid }
        val purchaseModelCopy = purchaseModel.copy(listMembers = ArrayList(list))
        setCollectionPurchaseUseCase(purchaseModelCopy)
    }

    fun onUpdateCheck(
        index: Int,
        item: UserCheckedUI
    ) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                listUserChecked = it.listUserChecked.toMutableList().apply {
                    this[index] = item.copy(isCheck = item.isCheck.not())
                }
            )
        }
    }

    fun onCategoryClicked() {
        val purchaseCollectionModel = _uiState.value.purchaseCollectionModelUI
        router().navigate(EditCollectionComposeFragmentDirections.fragmentCategory(purchaseCollectionModel))
    }

    fun onHistoryClicked() {
        val purchaseCollectionModel = _uiState.value.purchaseCollectionModelUI
        router().navigate(EditCollectionComposeFragmentDirections.fragmentHistory(purchaseCollectionModel))
    }

    private fun apiFirebaseUser() = viewModelScope.launchOnError {
        _uiState.emit(
            _uiState.value.copy(progress = Progress.Start)
        )

        val purchaseCollection =
            firebaseFirestorePurchaseCollectionUseCase(args.modelCollectionPurchase?.id)
        if (purchaseCollection != null) {
            _uiState.emit(
                _uiState.value.copy(purchaseCollectionModelUI = purchaseCollection.toPurchaseCollectionModelUI())
            )
        }

        userUseCase().collect { list ->
            _uiState.emit(
                _uiState.value.copy(
                    listUserChecked = list.map {
                        UserCheckedUI(
                            purchaseCollection?.listMembers?.contains(it.uid) ?: false,
                            it.toUserPurchaseModelUI()
                        )
                    },
                    progress = Progress.End
                )
            )
        }
    }
}
