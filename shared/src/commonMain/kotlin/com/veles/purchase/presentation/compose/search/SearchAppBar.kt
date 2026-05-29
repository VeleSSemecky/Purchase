package com.veles.purchase.presentation.compose.search

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

/**
 * Custom Search AppBar components for KMP
 *
 * Migrated from: /presentation/compose/search/SearchAppBar.kt
 *
 * These components provide search functionality in top app bars
 * matching the original design exactly.
 */

// Temporary color until we migrate Colors.kt
private val colorPrimary = Color(0xFF212121)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    navigationSearchIcon: @Composable (() -> Unit)? = null,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            if (navigationSearchIcon == null) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onCloseClicked()
                        onTextChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            } else {
                navigationSearchIcon()
            }
        },
        title = {
            TextField(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth(),
                value = text,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier,
                        text = "Search here...",
                        color = Color.White
                    )
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                ),
                singleLine = true,
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
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White.copy(alpha = 0.38f),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorPrimary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    searchWidgetState: MutableState<SearchWidgetState> = remember {
        mutableStateOf(value = SearchWidgetState.CLOSED)
    },
    title: @Composable (searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
    navigationIcon: @Composable (searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
    actions: @Composable RowScope.(searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {}
) {
    TopAppBar(
        title = {
            title(searchWidgetState)
        },
        navigationIcon = {
            navigationIcon(searchWidgetState)
        },
        actions = {
            actions(searchWidgetState)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorPrimary
        )
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
    title: @Composable (searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
    navigationIcon: @Composable (searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
    actions: @Composable RowScope.(searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {}
) {
    when (searchWidgetState.value) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                searchWidgetState = searchWidgetState,
                title = title,
                navigationIcon = navigationIcon,
                actions = actions
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
    title: @Composable (searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
    navigationIcon: @Composable (searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
    actions: @Composable RowScope.(searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {}
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
        title = title,
        navigationIcon = navigationIcon,
        actions = actions
    )
}
