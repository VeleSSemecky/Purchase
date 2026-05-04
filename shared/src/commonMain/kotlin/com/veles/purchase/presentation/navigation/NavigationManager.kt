package com.veles.purchase.presentation.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

sealed class NavigationEvent {
    data class NavigateTo(val route: Route) : NavigationEvent()
    object NavigateBack : NavigationEvent()
    object Logout : NavigationEvent()
    object LoginSuccess : NavigationEvent()
}

class NavigationManager(
    startDestination: Route = Route.Main
) {
    private val _backStack: SnapshotStateList<Route> = mutableStateListOf(startDestination)
    val backStack: SnapshotStateList<Route> get() = _backStack
    fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.NavigateTo -> _backStack.add(event.route)
            NavigationEvent.NavigateBack -> { _backStack.removeLastOrNull()
                // else: ignore, do not pop root
            }
            NavigationEvent.Logout -> {
                _backStack.clear()
                _backStack.add(Route.Login)
            }
            NavigationEvent.LoginSuccess -> {
                _backStack.clear()
                _backStack.add(Route.Main)
            }
        }
    }
}
