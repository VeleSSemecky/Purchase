package com.veles.purchase.presentation.model.purchase.compose.collection.edit

data class ComponentNameState(
    val collectionName: String,
    val isError: Boolean,
    val setCollectionName: (text: String) -> Unit
) {

    companion object {
        val PREVIEW_STATE = ComponentNameState(
            collectionName = "Collection Name",
            isError = false,
            setCollectionName = { }
        )
    }
}
