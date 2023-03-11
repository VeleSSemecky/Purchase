package com.veles.purchase.presentation.presentation.mvvm.purchase.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.usecase.logout.LogoutUseCase
import com.veles.purchase.domain.usecase.user.UserUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.extensions.launchOnError
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val router: Router
) : ViewModel() {

    private val _flowUserPurchaseModel = MutableStateFlow(UserPurchaseModel.EMPTY)
    val flowUserPurchaseModel: StateFlow<UserPurchaseModel>
        get() = _flowUserPurchaseModel.asStateFlow()

    init {
        getUserPurchase()
    }

    fun isNeedLogin(): Boolean {
        if (!userUseCase.isNeedLogin()) return false
        onLogoutClicked()
        return true
    }

    fun onSignOutGoogleSignInClient() = viewModelScope.launch {
        logoutUseCase()
        onLogoutClicked()
    }

    fun onHistoryClicked() = router().navigate(NavigationFragmentDirections.fragmentHistory())

    fun onSkuListClicked() = router().navigate(NavigationFragmentDirections.fragmentSkuList())
    fun onSettingPurchaseClicked() =
        router().navigate(NavigationFragmentDirections.fragmentSettingPurchaseCompose())

    private fun onLogoutClicked() = router().navigate(NavigationFragmentDirections.fragmentLogin())

    private fun getUserPurchase() = viewModelScope.launchOnError {
        _flowUserPurchaseModel.emit(userUseCase.getUserPurchase() ?: UserPurchaseModel.EMPTY)
    }
}
