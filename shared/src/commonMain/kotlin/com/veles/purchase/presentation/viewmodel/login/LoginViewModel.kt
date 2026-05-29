package com.veles.purchase.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * LoginViewModel for KMP
 * Handles Google Sign-In authentication
 *
 * Note: signInWithGoogle requires platform-specific activity/context
 * which is passed at call time, not injection time
 */
class LoginViewModel(private val authRepository: AuthWithGoogleRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    /**
     * Initiate Google Sign-In
     * @param signInCallback Platform-specific sign-in function that returns ID token
     */
    fun signInWithGoogle(signInCallback: suspend () -> Pair<String, String?>) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val idToken = signInCallback()
                authRepository.firebaseAuthWithGoogle(idToken)
                _state.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}

data class LoginState(val isLoading: Boolean = false, val isSuccess: Boolean = false, val error: String? = null)
