package com.veles.purchase.presentation.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.repository.auth.LogoutRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    /** 1–2 uppercase initials derived from displayName or email */
    val initials: String = "?"
)

class MainViewModel(
    private val auth: FirebaseAuth,
    private val logoutRepository: LogoutRepository
) : ViewModel() {

    private val _state = MutableStateFlow(buildState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private fun buildState(): MainUiState {
        val user = auth.currentUser
        val name = user?.displayName.orEmpty()
        val initials = name
            .split(" ")
            .mapNotNull { it.firstOrNull()?.uppercaseChar()?.toString() }
            .take(2)
            .joinToString("")
            .ifEmpty {
                user?.email?.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
            }
        return MainUiState(
            displayName = name.ifEmpty { "User" },
            email = user?.email.orEmpty(),
            photoUrl = user?.photoURL,
            initials = initials
        )
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            try {
                logoutRepository.logout()
            } finally {
                onLoggedOut()
            }
        }
    }
}
