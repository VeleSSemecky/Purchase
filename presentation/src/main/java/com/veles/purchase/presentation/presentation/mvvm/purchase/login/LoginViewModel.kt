package com.veles.purchase.presentation.presentation.mvvm.purchase.login

import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.usecase.auth.LoginUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel @Inject constructor(
    private val router: Router,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _flowIsShowError = MutableStateFlow(false)
    val flowIsShowError: StateFlow<Boolean>
        get() = _flowIsShowError.asStateFlow()

    private val _flowIsClickedGoogle = MutableStateFlow(false)
    val flowIsClickedGoogle: StateFlow<Boolean>
        get() = _flowIsClickedGoogle.asStateFlow()

    private val handler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            _flowIsClickedGoogle.emit(false)
            if (exception !is GetCredentialCancellationException) _flowIsShowError.emit(true)
        }
    }

    fun onErrorHideClicked() = viewModelScope.launch(handler) {
        _flowIsShowError.emit(false)
    }

    fun onLoginClicked() = viewModelScope.launch(handler) {
        _flowIsClickedGoogle.emit(true)
        loginUseCase()
        _flowIsClickedGoogle.emit(false)
        router().navigate(LoginFragmentDirections.fragmentNavigation())
    }
}
