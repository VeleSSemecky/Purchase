package com.veles.purchase.presentation.compose.search

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.presentation.compose.Colors

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Colors.colorPrimary
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {
                        onCloseClicked()
                        onTextChange("")
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onTextChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White,
                        modifier = Modifier.alpha(if (text.isEmpty()) 0.toFloat() else 1.toFloat())
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            )
        )
    }
}

@Composable
fun DefaultAppBar(
    searchWidgetState: MutableState<SearchWidgetState> = remember {
        mutableStateOf(value = SearchWidgetState.CLOSED)
    },
    content: @Composable (RowScope.(searchWidgetState: MutableState<SearchWidgetState>) -> Unit) = {
    }
) {
    TopAppBar(
        content = {
            content(searchWidgetState)
        },
        contentPadding = PaddingValues(0.dp),
        backgroundColor = Colors.colorPrimary
    )
}

@Composable
fun SearchWidgetAppBar(
    searchWidgetState: MutableState<SearchWidgetState> = remember {
        mutableStateOf(value = SearchWidgetState.CLOSED)
    },
    searchTextState: String = "",
    onTextChange: (String) -> Unit = {},
    onCloseClicked: () -> Unit = {},
    onSearchClicked: (String) -> Unit = {},
    content: @Composable (RowScope.(searchWidgetState: MutableState<SearchWidgetState>) -> Unit) = {
    }
) {
    when (searchWidgetState.value) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                searchWidgetState = searchWidgetState,
                content = content
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun SearchTopAppBar(
    searchTextState: String,
    searchWidgetState: MutableState<SearchWidgetState> =
        remember { mutableStateOf(value = SearchWidgetState.CLOSED) },
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit = {},
    onSearchClicked: (String) -> Unit = {},
    content: @Composable RowScope.(searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {}
) {
    SearchWidgetAppBar(
        searchWidgetState = searchWidgetState,
        searchTextState = searchTextState,
        onTextChange = {
            onTextChange(it)
        },
        onCloseClicked = {
            searchWidgetState.value = SearchWidgetState.CLOSED
            onCloseClicked()
        },
        onSearchClicked = {
            onSearchClicked(it)
        },
        content = content
    )
}

@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar()
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}

@Composable
@Preview
fun SearchTopAppBarPreview() {
    val searchWidgetState: MutableState<SearchWidgetState> =
        remember { mutableStateOf(value = SearchWidgetState.CLOSED) }
    val searchTextState: MutableState<String> =
        remember { mutableStateOf(value = "") }

    SearchWidgetAppBar(
        searchWidgetState = searchWidgetState,
        searchTextState = searchTextState.value,
        onTextChange = {
            searchTextState.value = it
        },
        onCloseClicked = {
            searchWidgetState.value = SearchWidgetState.CLOSED
        },
        onSearchClicked = {
            Log.d("Searched Text", it)
        }
    )
}
