package com.veles.purchase.presentation.compose.purchase.list

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.compose.DismissDirection
import com.veles.purchase.presentation.compose.DismissValue
import com.veles.purchase.presentation.compose.FractionalThreshold
import com.veles.purchase.presentation.compose.SwipeToDismiss
import com.veles.purchase.presentation.compose.rememberDismissState
import com.veles.purchase.presentation.compose.search.SearchTopAppBar
import com.veles.purchase.presentation.compose.search.SearchWidgetState
import com.veles.purchase.presentation.model.sort.SortPurchase
import com.veles.purchase.presentation.model.sort.toPurchaseComparator
import com.veles.purchase.presentation.mvvm.purchase.list.ListPurchaseViewModel
import com.veles.purchase.presentation.mvvm.purchase.list.PurchaseListUiState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseListScreen(
    collectionId: String,
    viewModel: ListPurchaseViewModel = koinViewModel(parameters = { parametersOf(collectionId) }),
    onNavigateBack: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToPurchaseDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchWidgetState by remember { mutableStateOf(SearchWidgetState.CLOSED) }
    var showSortSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = Colors.surface,
        topBar = {
            PurchaseListTopBar(
                title = uiState.collection.name,
                searchText = uiState.searchText,
                searchWidgetState = searchWidgetState,
                onSearchWidgetStateChanged = { searchWidgetState = it },
                onSearchTextChanged = viewModel::updateSearchText,
                onBackClick = onNavigateBack,
                onSettingsClick = onNavigateToSettings
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PurchaseListContent(
                purchases = uiState.purchases,
                sortPurchase = uiState.sortPurchase,
                searchText = uiState.searchText,
                settings = uiState.settings,
                newNamePurchase = uiState.newNamePurchase,
                onChecked = viewModel::onChecked,
                onDelete = viewModel::deletePurchase,
                onItemClick = onNavigateToPurchaseDetail,
                onShowSortSheet = { showSortSheet = true },
                onNewNameChange = viewModel::onNewNamePurchaseChanged,
                onAddPurchase = viewModel::insertAdd
            )
            
            if (uiState.progress == ListPurchaseViewModel.ProgressState.Start) {
                LoadingOverlay()
            }
        }
    }

    if (showSortSheet) {
        SortSelectionBottomSheet(
            selectedSort = uiState.sortPurchase,
            onSortSelected = {
                viewModel.setSortPurchase(it)
                showSortSheet = false
            },
            onDismiss = { showSortSheet = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PurchaseListTopBar(
    title: String,
    searchText: String,
    searchWidgetState: SearchWidgetState,
    onSearchWidgetStateChanged: (SearchWidgetState) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchWidgetStateMutable = remember { mutableStateOf(searchWidgetState) }

    LaunchedEffect(searchWidgetState) {
        if (searchWidgetStateMutable.value != searchWidgetState) {
            searchWidgetStateMutable.value = searchWidgetState
        }
    }

    Box(modifier = modifier) {
        SearchTopAppBar(
            searchTextState = searchText,
            searchWidgetState = searchWidgetStateMutable,
            onTextChange = onSearchTextChanged,
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = { widgetState ->
                IconButton(onClick = {
                    widgetState.value = SearchWidgetState.OPENED
                    onSearchWidgetStateChanged(SearchWidgetState.OPENED)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
                IconButton(onClick = onSettingsClick) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun PurchaseListContent(
    purchases: List<PurchaseModel>,
    sortPurchase: SortPurchase,
    searchText: String,
    settings: PurchaseSetting,
    newNamePurchase: String,
    onChecked: (PurchaseModel) -> Unit,
    onDelete: (PurchaseModel) -> Unit,
    onItemClick: (String) -> Unit,
    onShowSortSheet: () -> Unit,
    onNewNameChange: (String) -> Unit,
    onAddPurchase: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sortedPurchases = remember(purchases, sortPurchase) {
        purchases.sortedWith(sortPurchase.toPurchaseComparator())
    }

    Column(modifier = modifier.fillMaxSize()) {
        val listState = rememberLazyListState()

        if (sortedPurchases.isEmpty()) {
            EmptyListPlaceholder(
                isSearchMode = searchText.isNotEmpty(),
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stickyHeader(key = "sort_header", contentType = "header") {
                    SortIndicator(
                        sortPurchase = sortPurchase,
                        onSortClick = onShowSortSheet
                    )
                }

                items(
                    items = sortedPurchases,
                    key = { it.createId },
                    contentType = { "purchase_item" }
                ) { purchase ->
                    SwipeablePurchaseItem(
                        modifier = Modifier.animateItem(),
                        purchase = purchase,
                        isImageSettingEnabled = settings.isImage,
                        onChecked = { onChecked(purchase) },
                        onDelete = { onDelete(purchase) },
                        onItemClick = { onItemClick(purchase.createId) }
                    )
                }
            }
        }

        CreatePurchaseInput(
            value = newNamePurchase,
            onValueChange = onNewNameChange,
            onAdd = onAddPurchase
        )
    }
}

@Composable
private fun EmptyListPlaceholder(
    isSearchMode: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            val emptyTitle = if (!isSearchMode) "No purchases yet" else "No matches found"
            val emptySubTitle = if (!isSearchMode) 
                "Type a name below to add your first item" 
            else 
                "Try a different search term"

            Text(
                text = emptyTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Text(
                text = emptySubTitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SwipeablePurchaseItem(
    purchase: PurchaseModel,
    isImageSettingEnabled: Boolean,
    onChecked: () -> Unit,
    onDelete: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberDismissState()

    if (dismissState.isDismissed(DismissDirection.EndToStart) ||
        dismissState.isDismissed(DismissDirection.StartToEnd)
    ) {
        LaunchedEffect(purchase) {
            onDelete()
            dismissState.snapTo(DismissValue.Default)
        }
    }

    SwipeToDismiss(
        modifier = modifier.fillMaxWidth(),
        state = dismissState,
        background = {},
        dismissThresholds = { FractionalThreshold(0.7f) }
    ) {
        val elevation by animateDpAsState(if (dismissState.dismissDirection == null) 0.dp else 4.dp)
        PurchaseItem(
            purchase = purchase,
            elevation = elevation,
            isImageSettingEnabled = isImageSettingEnabled,
            onChecked = onChecked,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun LoadingOverlay() {
    Box(
        modifier = Modifier.fillMaxSize().background(Colors.progress).clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Colors.gr)
    }
}

@Composable
private fun CreatePurchaseInput(
    value: String,
    onValueChange: (String) -> Unit,
    onAdd: (String) -> Unit
) {
    val label = @Composable {
        Text(
            modifier = Modifier.alpha(0.60f),
            text = "Search or create purchase",
            color = Color.White.copy(alpha = 0.87f)
        )
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth().imePadding(),
        label = if (value.isNotEmpty()) label else null,
        placeholder = {
            Text(
                modifier = Modifier.alpha(0.60f),
                text = "Name purchase",
                color = Color.White
            )
        },
        textStyle = TextStyle(fontSize = MaterialTheme.typography.titleMedium.fontSize),
        singleLine = true,
        trailingIcon = {
            Row {
                IconButton(onClick = { if (value.isNotEmpty()) onValueChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Color.White,
                        modifier = Modifier.alpha(if (value.isEmpty()) 0f else 1f)
                    )
                }
                IconButton(onClick = { if (value.isNotEmpty()) onAdd(value) }) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.alpha(if (value.isEmpty()) 0f else 1f)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { if (value.isNotEmpty()) onAdd(value) }),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.White.copy(alpha = 0.60f),
            focusedIndicatorColor = Colors.gr.copy(alpha = 0.87f),
            focusedLabelColor = Colors.gr.copy(alpha = 0.87f)
        )
    )
}
