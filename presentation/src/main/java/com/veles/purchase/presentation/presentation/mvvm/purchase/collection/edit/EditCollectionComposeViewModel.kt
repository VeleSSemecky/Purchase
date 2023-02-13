package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.usecase.collection.FirebaseFirestorePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.SetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.user.UserUseCase
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.extensions.launchOnError
import com.veles.purchase.presentation.model.core.TextFieldModel
import com.veles.purchase.presentation.model.core.anyError
import com.veles.purchase.presentation.model.core.createTextFieldModel
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.PurchaseCollectionModelUI
import com.veles.purchase.presentation.model.purchase.toPurchaseCollectionModel
import com.veles.purchase.presentation.model.user.UserCheckedUI
import com.veles.purchase.presentation.model.user.toUserPurchaseModelUI
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class EditCollectionComposeViewModel @Inject constructor(
    private val args: EditCollectionComposeFragmentArgs,
    private val setCollectionPurchaseUseCase: SetCollectionPurchaseUseCase,
    private val firebaseFirestorePurchaseCollectionUseCase: FirebaseFirestorePurchaseCollectionUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    val flowCollectionName: MutableStateFlow<TextFieldModel<String>> =
        MutableStateFlow(emptyString().createTextFieldModel())

    val flowListUserChecked: MutableStateFlow<List<UserCheckedUI>> =
        MutableStateFlow(emptyList())

    private val flowPurchaseCollection: MutableStateFlow<PurchaseCollectionModel> =
        MutableStateFlow(PurchaseCollectionModelUI().toPurchaseCollectionModel())

    val flowProgress: MutableStateFlow<Progress> =
        MutableStateFlow(Progress.End)

    fun setCollectionName(name: String) = viewModelScope.launch {
        flowCollectionName.emit(name.createTextFieldModel(name.isEmpty()))
        flowPurchaseCollection.emit(flowPurchaseCollection.value.copy(name = name))
    }

    fun save(onSuccess: () -> Unit) = viewModelScope.launch {
        flowProgress.emit(Progress.Start)

        flowCollectionName.createTextFieldModel { isEmpty() }

        if (anyError(flowCollectionName)) {
            flowProgress.emit(Progress.End)
            return@launch
        }

        val purchaseCollection = flowPurchaseCollection.value
        apiFirebaseFirestore(purchaseCollection.copy(name = flowCollectionName.value.model))

        flowProgress.emit(Progress.End)
        onSuccess()
    }

    private suspend fun apiFirebaseFirestore(
        purchaseModel: PurchaseCollectionModel
    ): Boolean {
        val list = flowListUserChecked.value
            .filter { userChecked -> userChecked.isCheck }
            .map { userChecked -> userChecked.userPurchase.uid }
        val purchaseModelCopy = purchaseModel.copy(listMembers = ArrayList(list))
        return setCollectionPurchaseUseCase(purchaseModelCopy)
    }

    private fun apiFirebaseUser() = viewModelScope.launchOnError {
        flowProgress.emit(Progress.Start)

        val purchaseCollection =
            firebaseFirestorePurchaseCollectionUseCase(args.modelCollectionPurchase?.id)

        flowCollectionName.emit((purchaseCollection?.name ?: emptyString()).createTextFieldModel())
        if (purchaseCollection != null) flowPurchaseCollection.emit(purchaseCollection)

        userUseCase().collect { list ->
            flowListUserChecked.emit(
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

    fun onUpdateCheck(
        index: Int,
        item: UserCheckedUI
    ) = viewModelScope.launch {
        val list = flowListUserChecked.value.toMutableList()
        list[index] = item.copy(isCheck = item.isCheck.not())
        flowListUserChecked.emit(list)
    }

    init {
        apiFirebaseUser()
    }
}
