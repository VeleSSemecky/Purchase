package com.veles.purchase.presentation.compose.autocomplete

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TextSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onFocusChanged: (FocusState) -> Unit = {},
    onValueChanged: (String) -> Unit,
    colors: TextFieldColors,
    textStyle: TextStyle,
    label: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        colors = colors,
        textStyle = textStyle,
        modifier = modifier
//            .fillMaxWidth(.9f)
            .onFocusChanged { onFocusChanged(it) },
        value = value,
        onValueChange = { query ->
            onValueChanged(query)
        },
        label = label,
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { onClearClick() }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear",
                    tint = Color.White
                )
            }
        },
        keyboardActions = KeyboardActions(onDone = { onDoneActionClick() }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )
    )
}
