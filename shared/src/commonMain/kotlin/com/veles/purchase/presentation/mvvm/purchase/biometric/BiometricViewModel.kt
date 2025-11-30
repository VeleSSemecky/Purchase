package com.veles.purchase.presentation.mvvm.purchase.biometric

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.platform.biometric.BiometricAuthenticator
import com.veles.purchase.platform.biometric.BiometricResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for Biometric Authentication Screen
 *
 * Migrated from: BiometricComposeViewModel.kt
 *
 * Manages:
 * - Biometric availability check
 * - Authentication state
 * - Authentication results
 *
 * Phase 2.11 - Biometric screen migration
 */
class BiometricViewModel(
    private val biometricAuthenticator: BiometricAuthenticator
) : ViewModel() {

    private val _uiState = MutableStateFlow(BiometricUiState())
    val uiState: StateFlow<BiometricUiState> = _uiState.asStateFlow()

    init {
        checkBiometricAvailability()
    }

    private fun checkBiometricAvailability() {
        val isAvailable = biometricAuthenticator.isBiometricAvailable()
        val isEnrolled = biometricAuthenticator.isBiometricEnrolled()

        _uiState.update {
            it.copy(
                isBiometricAvailable = isAvailable,
                isBiometricEnrolled = isEnrolled
            )
        }
    }

    fun authenticate() {
        viewModelScope.launch {
            _uiState.update { it.copy(isAuthenticating = true) }

            val result = biometricAuthenticator.authenticate(
                title = "Biometric Authentication",
                subtitle = "Verify your identity to continue",
                negativeButtonText = "Cancel"
            )

            _uiState.update {
                it.copy(
                    isAuthenticating = false,
                    authenticationResult = result,
                    showResultDialog = true
                )
            }
        }
    }

    fun dismissResultDialog() {
        _uiState.update { it.copy(showResultDialog = false) }
    }

    fun resetAuthentication() {
        _uiState.update {
            it.copy(
                authenticationResult = null,
                showResultDialog = false
            )
        }
    }
}

/**
 * UI State for Biometric Screen
 */
data class BiometricUiState(
    val isBiometricAvailable: Boolean = false,
    val isBiometricEnrolled: Boolean = false,
    val isAuthenticating: Boolean = false,
    val authenticationResult: BiometricResult? = null,
    val showResultDialog: Boolean = false
)