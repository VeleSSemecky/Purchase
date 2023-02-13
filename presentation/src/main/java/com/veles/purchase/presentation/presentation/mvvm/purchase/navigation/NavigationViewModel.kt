package com.veles.purchase.presentation.presentation.mvvm.purchase.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.usecase.logout.LogoutUseCase
import com.veles.purchase.domain.usecase.user.UserUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _flowUserPurchaseModel = MutableStateFlow(UserPurchaseModel.EMPTY)
    val flowUserPurchaseModel: StateFlow<UserPurchaseModel>
        get() = _flowUserPurchaseModel.asStateFlow()

    init {
        viewModelScope.launch {
            _flowUserPurchaseModel.emit(getUserPurchase() ?: UserPurchaseModel.EMPTY)
        }
    }

    private suspend fun getUserPurchase() = userUseCase.getUserPurchase()

    fun isNeedLogin(): Boolean = userUseCase.isNeedLogin()

    fun onSignOutGoogleSignInClient() = viewModelScope.launch {
        logoutUseCase()
    }
}
