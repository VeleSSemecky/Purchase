package com.veles.purchase.presentation.model.core

import kotlinx.coroutines.flow.MutableStateFlow

data class PhotoStateModel<T>(
    val model: T,
    val isNeedDelete: Boolean = false
)

fun <T> T.createPhotoStateModel(isNeedDelete: Boolean = false) = PhotoStateModel(
    model = this,
    isNeedDelete = isNeedDelete
)

fun <T> List<T>?.createListPhotoStateModels(isNeedDelete: Boolean = false) =
    this?.map {
        PhotoStateModel(
            model = it,
            isNeedDelete = isNeedDelete
        )
    } ?: emptyList()

suspend fun <T> MutableStateFlow<List<PhotoStateModel<T>>>.emitPhotoStateModel(model: T) {
    this.emit(
        this.value.toMutableList().apply {
            add(model.createPhotoStateModel())
        }
    )
}

suspend fun <T> MutableStateFlow<List<PhotoStateModel<T>>>.changePhotoStateModel(
    model: T,
    isNeedDelete: Boolean = false
) {
    val list = this.value.toMutableList()
    val index = list.map { it.model }.indexOf(model)
    list[index] = model.createPhotoStateModel(isNeedDelete)
    this.emit(list)
}

fun <T> MutableStateFlow<List<PhotoStateModel<T>>>.getModel(
    predicate: (PhotoStateModel<T>) -> Boolean = { true }
) = this.value
    .filter { predicate(it) }
    .map { it.model }
