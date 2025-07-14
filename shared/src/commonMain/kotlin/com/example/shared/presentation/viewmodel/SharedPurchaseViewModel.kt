package com.example.shared.presentation.viewmodel

import com.example.shared.di.DatabaseModule
import com.example.shared.domain.model.purchase.PurchaseModel
import com.example.shared.domain.usecase.AddPurchaseUseCase
import com.example.shared.domain.usecase.DeletePurchaseUseCase
import com.example.shared.domain.usecase.GetAllPurchasesUseCase
import com.example.shared.domain.usecase.UpdatePurchaseStatusUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Shared ViewModel for iOS that can be used from Swift
 */
class SharedPurchaseViewModel {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val repository = DatabaseModule.purchaseRepository

    private val getAllPurchasesUseCase = GetAllPurchasesUseCase(repository)
    private val addPurchaseUseCase = AddPurchaseUseCase(repository)
    private val updatePurchaseStatusUseCase = UpdatePurchaseStatusUseCase(repository)
    private val deletePurchaseUseCase = DeletePurchaseUseCase(repository)

    private val _purchases = MutableStateFlow<List<PurchaseModel>>(emptyList())
    val purchases: StateFlow<List<PurchaseModel>> = _purchases.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadPurchases()
    }

    private fun loadPurchases() {
        scope.launch {
            _isLoading.value = true
            getAllPurchasesUseCase().collect { purchaseList ->
                _purchases.value = purchaseList
                _isLoading.value = false
            }
        }
    }

    fun addPurchase(text: String, count: String, price: String = "") {
        scope.launch {
            addPurchaseUseCase(text, count, price)
        }
    }

    fun updatePurchaseStatus(purchase: PurchaseModel, isChecked: Boolean) {
        scope.launch {
            updatePurchaseStatusUseCase(purchase, isChecked)
        }
    }

    fun deletePurchase(purchaseId: String) {
        scope.launch {
            deletePurchaseUseCase(purchaseId)
        }
    }

    fun onCleared() {
//        scope.coroutineContext.cancel()
    }
}
