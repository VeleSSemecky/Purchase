package com.veles.purchase.presentation.model.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class TextFieldModel<T>(
    val model: T,
    val isError: Boolean = false
)

fun <T> T.createTextFieldModel(isError: Boolean = false) = TextFieldModel(
    model = this,
    isError = isError
)

suspend inline fun <T> MutableStateFlow<TextFieldModel<T>>.createTextFieldModel(
    model: (T) -> T = { it },
    isError: T.() -> Boolean
) {
    val data = model(this.value.model)
    emit(
        TextFieldModel(
            model = data,
            isError = isError(data)
        )
    )
}

fun <T> anyError(vararg items: StateFlow<TextFieldModel<T>>) = items.any { it.value.isError }
