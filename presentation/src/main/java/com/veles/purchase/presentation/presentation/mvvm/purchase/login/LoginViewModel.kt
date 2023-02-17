package com.veles.purchase.presentation.presentation.mvvm.purchase.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.veles.purchase.domain.usecase.FirebaseSetUserUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import javax.inject.Inject
import kotlinx.coroutines.launch

class LoginViewModel @Inject constructor(
    private val router: Router,
    private val googleSignInClient: GoogleSignInClient,
    private val firebaseSetUserUseCase: FirebaseSetUserUseCase
) : ViewModel() {

    fun googleSignInIntent(): Intent = googleSignInClient.signInIntent

    fun firebaseAuthWithGoogle(idToken: String) = viewModelScope.launch {
        firebaseSetUserUseCase(idToken)
        router().navigate(LoginFragmentDirections.fragmentNavigation())
    }
}
