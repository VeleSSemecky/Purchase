package com.veles.purchase.presentation.presentation.mvvm.purchase.login

import android.app.Activity
import android.util.Log
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.usecase.auth.LoginUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.data.result.GoogleSignIn
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
            Log.e("LoginViewModel", "Error: $exception", exception)
            _flowIsClickedGoogle.emit(false)
            if (exception !is GetCredentialCancellationException) _flowIsShowError.emit(true)
        }
    }

    fun onErrorHideClicked() = viewModelScope.launch(handler) {
        _flowIsShowError.emit(false)
    }

    fun onLoginClicked(context: Activity) = viewModelScope.launch(handler) {
        _flowIsClickedGoogle.emit(true)
        val idToken = GoogleSignIn(context)
        loginUseCase(idToken)
        _flowIsClickedGoogle.emit(false)
        router().navigate(LoginFragmentDirections.fragmentNavigation())
    }
}
