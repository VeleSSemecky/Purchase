package com.veles.purchase.presentation.presentation.mvvm.purchase.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.AddLazyPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.CheckPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.DeletePurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.GetPurchasesUseCase
import com.veles.purchase.domain.usecase.purchase.MoveForLaterPurchaseUseCase
import com.veles.purchase.domain.usecase.setting.GetSettingUseCase
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.extensions.launchOnError
import com.veles.purchase.presentation.model.event.SortPurchaseEvent
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.PurchaseModelUI
import com.veles.purchase.presentation.model.purchase.toPurchaseCollectionModelUI
import com.veles.purchase.presentation.model.purchase.toPurchaseModel
import com.veles.purchase.presentation.model.sort.SortPurchase
import com.veles.purchase.presentation.model.sort.toPurchaseComparator
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListPurchaseViewModel @Inject constructor(
    private val args: ListPurchaseFragmentArgs,
    private val sharedFlowBus: SharedFlowBus,
    private val getPurchasesUseCase: GetPurchasesUseCase,
    private val deletePurchaseUseCase: DeletePurchaseUseCase,
    private val addLazyPurchaseUseCase: AddLazyPurchaseUseCase,
    private val checkPurchaseUseCase: CheckPurchaseUseCase,
    private val getCollectionPurchaseUseCase: GetCollectionPurchaseUseCase,
    private val getSettingUseCase: GetSettingUseCase,
    private val moveForLaterPurchaseUseCase: MoveForLaterPurchaseUseCase,
    private val router: Router
) : ViewModel() {

    private val _flowProgress: MutableStateFlow<Progress> = MutableStateFlow(Progress.End)
    val flowProgress: StateFlow<Progress>
        get() = _flowProgress.asStateFlow()

    private val _flowListPurchaseModels: MutableStateFlow<List<PurchaseModel>> =
        MutableStateFlow(emptyList())
    val flowListPurchaseModels: StateFlow<List<PurchaseModel>>
        get() = _flowListPurchaseModels.asStateFlow()

    private val _flowSortPurchase: MutableStateFlow<SortPurchase> =
        MutableStateFlow(SortPurchase.SORTING_UNCHECK)
    val flowSortPurchase: StateFlow<SortPurchase>
        get() = _flowSortPurchase.asStateFlow()

    private val _flowSearchText: MutableStateFlow<String> = MutableStateFlow(emptyString())
    val flowSearchText: StateFlow<String>
        get() = _flowSearchText.asStateFlow()

    private val _flowCollectionPurchase: MutableStateFlow<PurchaseCollectionModel> =
        MutableStateFlow(PurchaseCollectionModel.EMPTY)
    val flowCollectionPurchase: StateFlow<PurchaseCollectionModel>
        get() = _flowCollectionPurchase.asStateFlow()

    private val _flowNewNamePurchase: MutableStateFlow<String> =
        MutableStateFlow(emptyString())
    val flowNewNamePurchase: StateFlow<String>
        get() = _flowNewNamePurchase.asStateFlow()

    private val _flowPurchaseSetting = MutableStateFlow(PurchaseSetting())
    val flowPurchaseSetting: StateFlow<PurchaseSetting>
        get() = _flowPurchaseSetting.asStateFlow()

    init {
        getCollectionPurchase()
        getPurchase(emptyString())
        getSortPurchase()
        getSettingsPurchase()
    }

    fun onSettingsClicked() = router().navigate(
        ListPurchaseFragmentDirections.fragmentEditCollection(
            flowCollectionPurchase.value.toPurchaseCollectionModelUI()
        )
    )

    fun onNewNamePurchaseChanged(text: String = emptyString()) = viewModelScope.launchOnError {
        _flowNewNamePurchase.emit(text)
        getPurchase(text)
    }

    fun onItemClicked(item: PurchaseModel) = router().navigate(
        ListPurchaseFragmentDirections.fragmentAddPurchase(
            purchaseCollectionId = flowCollectionPurchase.value.id,
            purchaseId = item.createId
        )
    )

    fun updateSearchText(
        text: String
    ) = viewModelScope.launchOnError {
        _flowSearchText.emit(text)
        getPurchase(text)
    }

    fun onChecked(
        purchaseModel: PurchaseModel
    ) = viewModelScope.launchOnError {
        checkPurchaseUseCase(
            args.purchaseCollectionId,
            purchaseModel
        )
    }

    fun insertAdd(
        purchaseName: String
    ) = viewModelScope.launchOnError {
        _flowProgress.emit(Progress.Start)

        _flowNewNamePurchase.emit(emptyString())

        val purchaseModel = PurchaseModelUI(
            text = purchaseName
        )

        addLazyPurchaseUseCase(
            purchaseModel.toPurchaseModel(),
            args.purchaseCollectionId
        )

        getPurchase(purchaseName)

        _flowProgress.emit(Progress.End)
    }

    fun apiFirebaseRemoveRepository(
        purchaseModel: PurchaseModel
    ) = viewModelScope.launchOnError {
        _flowProgress.emit(Progress.Start)

        deletePurchaseUseCase(
            purchaseModel,
            args.purchaseCollectionId
        )

        _flowProgress.emit(Progress.End)
    }

    fun onBackClicked() {
        router().popBackStack()
    }

    fun onLongClicked(
        purchaseModel: PurchaseModel
    ) = viewModelScope.launchOnError {
        _flowProgress.emit(Progress.Start)
        moveForLaterPurchaseUseCase(
            purchaseModel,
            args.purchaseCollectionId
        )
        _flowProgress.emit(Progress.End)
    }

    fun onSortClicked() =
        router().navigate(ListPurchaseFragmentDirections.fragmentSort())

    private fun getPurchase(
        search: String = emptyString()
    ) = getPurchasesUseCase(args.purchaseCollectionId, search).onEach { list ->
        _flowListPurchaseModels.emit(list)
    }.launchIn(viewModelScope)

    private fun getCollectionPurchase() = viewModelScope.launchOnError {
        _flowCollectionPurchase.emit(getCollectionPurchaseUseCase(args.purchaseCollectionId))
    }

    private fun getSortPurchase() = sharedFlowBus.getSharedFlow(SortPurchaseEvent::class).onEach {
        _flowSortPurchase.emit(it.sortPurchase)
    }.onEach {
        _flowListPurchaseModels.emit(
            flowListPurchaseModels.value.sortedWith(it.sortPurchase.toPurchaseComparator())
        )
    }.launchIn(viewModelScope)

    private fun getSettingsPurchase() = viewModelScope.launchOnError {
        _flowPurchaseSetting.emitAll(getSettingUseCase())
    }
}
