package com.veles.purchase.presentation.mvvm.purchase.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.usecase.user.UserUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * UI State for the Collection Members screen
 */
data class CollectionMembersUiState(
    val members: List<MemberItem> = emptyList(),
    val isLoading: Boolean = false
)

data class MemberItem(
    val user: UserPurchaseModel,
    val isSelected: Boolean
)

/**
 * ViewModel for managing collection members.
 */
class CollectionMembersViewModel(
    initialSelectedIds: List<String>,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _selectedIds = MutableStateFlow(initialSelectedIds.toSet())
    private val _allUsers = MutableStateFlow<List<UserPurchaseModel>>(emptyList())

    val uiState: StateFlow<CollectionMembersUiState> = combine(
        _allUsers,
        _selectedIds,
        _isLoading
    ) { allUsers, selectedIds, loading ->
        CollectionMembersUiState(
            members = allUsers.map { user ->
                MemberItem(
                    user = user,
                    isSelected = selectedIds.contains(user.uid)
                )
            },
            isLoading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CollectionMembersUiState(isLoading = true)
    )

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            userUseCase().collect { users ->
                _allUsers.value = users
                _isLoading.value = false
            }
        }
    }

    fun toggleMember(user: UserPurchaseModel, isSelected: Boolean) {
        _selectedIds.update { current ->
            if (isSelected) current + user.uid else current - user.uid
        }
    }

    fun getSelectedIds(): List<String> = _selectedIds.value.toList()
}
