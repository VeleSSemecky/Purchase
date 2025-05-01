package com.veles.purchase.presentation.model.purchase.compose.collection.edit

import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.model.core.TextFieldModel
import com.veles.purchase.presentation.model.core.createTextFieldModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ComponentNameState(
    val flowCollectionName: StateFlow<TextFieldModel<String>>,
    val setCollectionName: (text: String) -> Unit

) {

    companion object {
        val PREVIEW_STATE = ComponentNameState(
            flowCollectionName = MutableStateFlow(emptyString().createTextFieldModel()),
            setCollectionName = { }
        )
    }
}
