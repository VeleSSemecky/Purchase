package com.veles.purchase.presentation.compose.search

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.material3.contentColorFor
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
        windowInsets = WindowInsets.Companion.systemBars
            .only(WindowInsetsSides.Top),
        navigationIcon = {
            if (navigationSearchIcon == null)
                IconButton(
                    modifier = Modifier
                        .alpha(0.60f),
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
            else navigationSearchIcon()
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
                        modifier = Modifier
                            .alpha(0.60f),
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
//            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.White,
//                backgroundColor = Color.Transparent,
//                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
//            )
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White.copy(alpha = 0.38f),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                )
            )
        },
//        navigationIcon = {
//            IconButton(
//                modifier = Modifier
//                    .alpha(0.60f),
//                onClick = {
//                    onCloseClicked()
//                    onTextChange("")
//                }
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
//                    contentDescription = "Search Icon",
//                    tint = Color.White
//                )
//            }
//        },
//        actions = {
//
//        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.colorPrimary
        )
    )
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(56.dp),
////        elevation = AppBarDefaults.TopAppBarElevation,
//        color = Colors.colorPrimary
//    ) {
//
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    searchWidgetState: MutableState<SearchWidgetState> = remember {
        mutableStateOf(value = SearchWidgetState.CLOSED)
    },
    title: @Composable (searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
    navigationIcon: @Composable (searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
    actions: @Composable RowScope.(searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},

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
            containerColor = Colors.colorPrimary
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
    actions: @Composable RowScope.(searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
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
    actions: @Composable RowScope.(searchWidgetState: MutableState<SearchWidgetState>) -> Unit = {},
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
