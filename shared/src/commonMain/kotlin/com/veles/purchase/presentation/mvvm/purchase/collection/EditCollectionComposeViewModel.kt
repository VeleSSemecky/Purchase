package com.veles.purchase.presentation.mvvm.purchase.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.collection.SetCollectionPurchaseUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ViewModel for Collection Edit/Add Screen
 * Migrated from presentation module - original name: EditCollectionComposeViewModel
 */
class EditCollectionComposeViewModel(
    private val collectionId: String,
    private val getCollectionPurchaseUseCase: GetCollectionPurchaseUseCase,
    private val setCollectionPurchaseUseCase: SetCollectionPurchaseUseCase
) : ViewModel() {

    private val _flowProgress = MutableStateFlow(ProgressState.End)
    val flowProgress: StateFlow<ProgressState> = _flowProgress.asStateFlow()

    private val _flowCollectionModel = MutableStateFlow(PurchaseCollectionModel.EMPTY)

    val flowCollectionName: StateFlow<String> = _flowCollectionModel
        .map { it.name }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val flowIsNameError: StateFlow<Boolean> = MutableStateFlow(false)

    val isNewCollection: Boolean
        get() = collectionId.isEmpty()

    init {
        loadCollection()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun loadCollection() {
        viewModelScope.launch {
            _flowProgress.emit(ProgressState.Start)

            if (collectionId.isNotEmpty()) {
                val collection = getCollectionPurchaseUseCase(collectionId)
                if (collection != null) {
                    _flowCollectionModel.emit(collection)
                }
            } else {
                val newCollection = PurchaseCollectionModel(
                    id = Uuid.random().toString().uppercase(),
                    name = "",
                    creator = com.veles.purchase.domain.model.user.UserPurchaseModel.EMPTY,
                    categoryModels = emptyList(),
                    listMembers = emptyList()
                )
                _flowCollectionModel.emit(newCollection)
            }

            _flowProgress.emit(ProgressState.End)
        }
    }

    fun onCollectionNameChange(name: String) {
        viewModelScope.launch {
            _flowCollectionModel.update { it.copy(name = name) }
            (flowIsNameError as MutableStateFlow).emit(name.isEmpty())
        }
    }

    suspend fun onSaveClicked(): Boolean {
        if (_flowCollectionModel.value.name.isBlank()) {
            (flowIsNameError as MutableStateFlow).emit(true)
            return false
        }

        _flowProgress.emit(ProgressState.Start)
        setCollectionPurchaseUseCase(_flowCollectionModel.value)
        _flowProgress.emit(ProgressState.End)
        return true
    }

    enum class ProgressState {
        Start,
        End
    }
}

