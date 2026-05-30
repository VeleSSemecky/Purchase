package com.veles.purchase.presentation.model

sealed class UiEvent {
    data class ShowError(val message: String) : UiEvent()
    object NavigateBack : UiEvent()
}
