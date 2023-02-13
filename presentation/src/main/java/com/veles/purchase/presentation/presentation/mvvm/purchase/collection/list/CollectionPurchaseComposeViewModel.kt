package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.usecase.collection.FirebaseFirestorePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.PurchaseCollectionDeleteUseCase
import com.veles.purchase.presentation.model.progress.Progress
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CollectionPurchaseComposeViewModel @Inject constructor(
    private val firebaseFirestorePurchaseCollectionUseCase: FirebaseFirestorePurchaseCollectionUseCase,
    private val purchaseCollectionDeleteUseCase: PurchaseCollectionDeleteUseCase
) : ViewModel() {

    val stateFlowProgress: MutableStateFlow<Progress> = MutableStateFlow(Progress.End)

    val stateFlowListPurchaseCollections: MutableStateFlow<List<PurchaseCollectionModel>> =
        MutableStateFlow(emptyList())

    val stateFlowDeletePurchaseCollections: MutableStateFlow<PurchaseCollectionModel?> =
        MutableStateFlow(null)

    init {
        apiFirebasePurchase()
    }

    fun onDeletePurchaseCollections(
        purchaseCollection: PurchaseCollectionModel?
    ) = viewModelScope.launch {
        stateFlowDeletePurchaseCollections.emit(purchaseCollection)
    }

    fun apiFirebaseRemovePurchaseCollection(
        item: PurchaseCollectionModel
    ) = viewModelScope.launch {
        purchaseCollectionDeleteUseCase(item)
    }

    private fun apiFirebasePurchase() = viewModelScope.launch {
        stateFlowProgress.emit(Progress.Start)
        firebaseFirestorePurchaseCollectionUseCase().collect {
            stateFlowListPurchaseCollections.emit(it)
            stateFlowProgress.emit(Progress.End)
        }
    }
}
