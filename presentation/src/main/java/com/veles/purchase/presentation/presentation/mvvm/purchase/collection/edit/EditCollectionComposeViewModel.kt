package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.usecase.collection.FirebaseFirestorePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.SetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.user.UserUseCase
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.extensions.launchOnError
import com.veles.purchase.presentation.model.core.TextFieldModel
import com.veles.purchase.presentation.model.core.anyError
import com.veles.purchase.presentation.model.core.createTextFieldModel
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.PurchaseCollectionModelUI
import com.veles.purchase.presentation.model.purchase.toPurchaseCategoryModel
import com.veles.purchase.presentation.model.purchase.toPurchaseCategoryModelUI
import com.veles.purchase.presentation.model.purchase.toPurchaseCollectionModel
import com.veles.purchase.presentation.model.purchase.toPurchaseCollectionModelUI
import com.veles.purchase.presentation.model.sort.SortPurchase
import com.veles.purchase.presentation.model.user.UserCheckedUI
import com.veles.purchase.presentation.model.user.toUserPurchaseModelUI
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

const val CATEGORY_MODELS_KEY = "CATEGORY_MODELS_KEY"

class EditCollectionComposeViewModel @Inject constructor(
    private val args: EditCollectionComposeFragmentArgs,
    private val setCollectionPurchaseUseCase: SetCollectionPurchaseUseCase,
    private val firebaseFirestorePurchaseCollectionUseCase: FirebaseFirestorePurchaseCollectionUseCase,
    private val userUseCase: UserUseCase,
    private val router: Router
) : ViewModel() {

    val flowCollectionName: MutableStateFlow<TextFieldModel<String>> =
        MutableStateFlow(emptyString().createTextFieldModel())

    private val _flowListUserChecked: MutableStateFlow<List<UserCheckedUI>> = MutableStateFlow(emptyList())
    val flowListUserChecked: StateFlow<List<UserCheckedUI>>
        get() = _flowListUserChecked.asStateFlow()

    private val flowPurchaseCollection: MutableStateFlow<PurchaseCollectionModel> =
        MutableStateFlow(PurchaseCollectionModelUI().toPurchaseCollectionModel())

    val flowProgress: MutableStateFlow<Progress> =
        MutableStateFlow(Progress.End)

    init {
        apiFirebaseUser()
        router().currentBackStackEntry?.savedStateHandle?.getStateFlow(CATEGORY_MODELS_KEY,
            flowPurchaseCollection.value.categoryModels.map { it.toPurchaseCategoryModelUI() }
        )?.onEach { list ->
            flowPurchaseCollection.emit(flowPurchaseCollection.value.copy(categoryModels = list.map { it.toPurchaseCategoryModel() }))
        }?.launchIn(viewModelScope)
    }

    fun setCollectionName(name: String) = viewModelScope.launch {
        flowCollectionName.emit(name.createTextFieldModel(name.isEmpty()))
        flowPurchaseCollection.emit(flowPurchaseCollection.value.copy(name = name))
    }

    fun save() = viewModelScope.launch {
        flowProgress.emit(Progress.Start)

        flowCollectionName.createTextFieldModel { isEmpty() }

        if (anyError(flowCollectionName)) {
            flowProgress.emit(Progress.End)
            return@launch
        }

        val purchaseCollection = flowPurchaseCollection.value
        apiFirebaseFirestore(purchaseCollection.copy(name = flowCollectionName.value.model))

        flowProgress.emit(Progress.End)
        router().popBackStack()
    }

    private suspend fun apiFirebaseFirestore(
        purchaseModel: PurchaseCollectionModel
    ) {
        val list = flowListUserChecked.value
            .filter { userChecked -> userChecked.isCheck }
            .map { userChecked -> userChecked.userPurchase.uid }
        val purchaseModelCopy = purchaseModel.copy(listMembers = ArrayList(list))
        setCollectionPurchaseUseCase(purchaseModelCopy)
    }

    fun onUpdateCheck(
        index: Int,
        item: UserCheckedUI
    ) = viewModelScope.launch {
        val list = flowListUserChecked.value.toMutableList()
        list[index] = item.copy(isCheck = item.isCheck.not())
        _flowListUserChecked.emit(list)
    }

    fun onCategoryClicked() {
        val purchaseCollectionModel = flowPurchaseCollection.value.toPurchaseCollectionModelUI()
        router().navigate(EditCollectionComposeFragmentDirections.fragmentCategory(purchaseCollectionModel))
    }

    private fun apiFirebaseUser() = viewModelScope.launchOnError {
        flowProgress.emit(Progress.Start)

        val purchaseCollection =
            firebaseFirestorePurchaseCollectionUseCase(args.modelCollectionPurchase?.id)

        flowCollectionName.emit((purchaseCollection?.name ?: emptyString()).createTextFieldModel())
        if (purchaseCollection != null) flowPurchaseCollection.emit(purchaseCollection)

        userUseCase().collect { list ->
            _flowListUserChecked.emit(
                list.map {
                    UserCheckedUI(
                        purchaseCollection?.listMembers?.contains(it.uid) ?: false,
                        it.toUserPurchaseModelUI()
                    )
                }
            )
            flowProgress.emit(Progress.End)
        }
    }
}
