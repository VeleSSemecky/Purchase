package com.veles.purchase.presentation.compose.purchase.list

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.compose.DismissDirection
import com.veles.purchase.presentation.compose.DismissValue
import com.veles.purchase.presentation.compose.FractionalThreshold
import com.veles.purchase.presentation.compose.SwipeToDismiss
import com.veles.purchase.presentation.compose.rememberDismissState
import com.veles.purchase.presentation.compose.search.SearchTopAppBar
import com.veles.purchase.presentation.compose.search.SearchWidgetState
import com.veles.purchase.presentation.compose.textStyle1
import com.veles.purchase.presentation.compose.textStyle2
import com.veles.purchase.presentation.mvvm.purchase.list.PurchaseListViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Purchase List Screen - shows purchases within a collection
 *
 * Migrated from: /presentation/.../mvvm/purchase/list/ListPurchaseFragment.kt
 *
 * Features:
 * - List of purchases with checkboxes
 * - Search functionality (SearchTopAppBar)
 * - Swipe to delete (Custom SwipeToDismiss with 0.7f threshold)
 * - Add new purchase inline
 * - Category chips (if available)
 * - Photo indicators (if available)
 * - Sort functionality
 *
 * Phase 2.6 - Purchase List feature migration
 * FIXED: Now using custom components matching original exactly
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseListScreen(
    collectionId: String,
    viewModel: PurchaseListViewModel = koinViewModel(parameters = { parametersOf(collectionId) }),
    onNavigateBack: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToPurchaseDetail: (String) -> Unit = {}
) {
    val collection by viewModel.flowCollectionPurchase.collectAsState()
    val searchText by viewModel.flowSearchText.collectAsState()
    val searchWidgetState = remember { mutableStateOf(SearchWidgetState.CLOSED) }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = Colors.surface,
        topBar = {
            SearchTopAppBar(
                searchTextState = searchText,
                searchWidgetState = searchWidgetState,
                onTextChange = { viewModel.updateSearchText(it) },
                onCloseClicked = { },
                onSearchClicked = { },
                navigationIcon = { _ ->
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                title = { _ ->
                    Text(
                        text = collection.name,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = { widgetState ->
                    IconButton(onClick = { widgetState.value = SearchWidgetState.OPENED }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Content(
                viewModel = viewModel,
                onNavigateToPurchaseDetail = onNavigateToPurchaseDetail
            )
            Progress(viewModel = viewModel)
        }
    }
}

@Composable
private fun Progress(viewModel: PurchaseListViewModel) {
    val progress by viewModel.flowProgress.collectAsState()
    if (progress != PurchaseListViewModel.ProgressState.Start) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.progress)
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Colors.gr)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    viewModel: PurchaseListViewModel,
    onNavigateToPurchaseDetail: (String) -> Unit
) {
    val purchases by viewModel.flowListPurchaseModels.collectAsState()
    val sortByChecked by viewModel.flowSortByChecked.collectAsState()

    // Apply simple sorting
    val sortedPurchases = remember(purchases, sortByChecked) {
        if (sortByChecked) {
            purchases.sortedBy { it.isChecked }
        } else {
            purchases
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Sort indicator
        SortIndicator(
            sortByChecked = sortByChecked,
            onSortClick = { viewModel.toggleSortByChecked() }
        )

        // Purchase list with custom SwipeToDismiss
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = sortedPurchases,
                key = { it.createId }
            ) { purchase ->
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                    dismissState.isDismissed(DismissDirection.StartToEnd)
                ) {
                    LaunchedEffect(purchase) {
                        viewModel.deletePurchase(purchase)
                        dismissState.snapTo(DismissValue.Default)
                    }
                }

                SwipeToDismiss(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    state = dismissState,
                    background = {},
                    dismissThresholds = { FractionalThreshold(0.7f) }
                ) {
                    val elevation = animateDpAsState(
                        if (dismissState.dismissDirection == null) 0.dp else 4.dp
                    ).value

                    PurchaseItem(
                        purchase = purchase,
                        elevation = elevation,
                        viewModel = viewModel,
                        onItemClick = { onNavigateToPurchaseDetail(purchase.createId) }
                    )
                }
            }
        }

        // Create new purchase input
        CreatePurchaseInput(viewModel = viewModel)
    }
}

@Composable
private fun SortIndicator(
    sortByChecked: Boolean,
    onSortClick: () -> Unit
) {
    Text(
        text = if (sortByChecked) "Sorted: Unchecked First" else "Sorted: Default",
        color = Colors.gr,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSortClick() }
            .padding(16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PurchaseItem(
    purchase: PurchaseModel,
    elevation: Dp,
    viewModel: PurchaseListViewModel,
    onItemClick: () -> Unit
) {
    val purchaseSetting by viewModel.flowPurchaseSetting.collectAsState()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Colors.colorAccent
        ),
        // shape = purchaseSetting.toShape(), // TODO: Add shape support when needed
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .combinedClickable(
                onClick = onItemClick,
                onLongClick = { /* TODO: Long press action */ }
            )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val (
                referenceTextTitle,
                referenceTextDescription,
                referenceIconCheck,
                referenceChipCategory,
            ) = createRefs()
            createVerticalChain(referenceTextTitle, referenceTextDescription, referenceChipCategory, chainStyle = ChainStyle.Packed)

            Text(
                text = purchase.text,
                fontSize = 18.sp,
                style = textStyle1(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .constrainAs(referenceTextTitle) {
                        start.linkTo(parent.start)
                        end.linkTo(referenceIconCheck.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(referenceTextDescription.top)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = purchase.count,
                fontSize = 14.sp,
                style = textStyle2(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .constrainAs(referenceTextDescription) {
                        start.linkTo(parent.start)
                        end.linkTo(referenceIconCheck.start)
                        top.linkTo(referenceTextTitle.bottom)
                        bottom.linkTo(referenceChipCategory.top)
                        width = Dimension.fillToConstraints
                        visibility = if (purchase.count.isNotEmpty()) Visibility.Visible else Visibility.Gone
                    }
            )

            FlowRow(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp
                    )
                    .constrainAs(referenceChipCategory) {
                        start.linkTo(parent.start)
                        end.linkTo(referenceIconCheck.start)
                        top.linkTo(referenceTextDescription.bottom)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.wrapContent
                        horizontalBias = 0f
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryChip(item = purchase)
                PhotoChip(item = purchase)
            }

            Box(
                modifier = Modifier
                    .clickable {
                        viewModel.onChecked(purchase)
                    }
                    .constrainAs(referenceIconCheck) {
                        start.linkTo(referenceTextTitle.end)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Checkbox(
                    checked = purchase.isChecked,
                    onCheckedChange = {
                        viewModel.onChecked(purchase)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Colors.gr,
                        uncheckedColor = Colors.gr,
                        checkmarkColor = Color.Black
                    )
                )
            }
        }
    }
}

@Composable
private fun PhotoChip(item: PurchaseModel) {
    if (item.listImage.isEmpty()) return
    Box(
        modifier = Modifier
            .padding(top = 6.dp)
            .background(
                color = Colors.gr.copy(alpha = 0.1f),
                shape = CircleShape
            )
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "📷",
            fontSize = 12.sp,
            color = Colors.gr,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun CategoryChip(item: PurchaseModel) {
    if (item.purchaseCategoryModel == null) return
    Box(
        modifier = Modifier
            .padding(top = 6.dp)
            .background(
                color = Colors.gr.copy(alpha = 0.1f),
                shape = CircleShape
            )
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.purchaseCategoryModel?.name ?: "Uncategorized",
            fontSize = 12.sp,
            style = TextStyle(
                color = Colors.gr,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
        )
    }
}

@Composable
private fun CreatePurchaseInput(viewModel: PurchaseListViewModel) {
    val newPurchaseName by viewModel.flowNewNamePurchase.collectAsState()

    val label = @Composable {
        Text(
            modifier = Modifier.alpha(0.60f),
            text = "Search or create purchase",
            color = Color.White.copy(alpha = 0.87f)
        )
    }

    TextField(
        value = newPurchaseName,
        onValueChange = { viewModel.onNewNamePurchaseChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(),
        label = if (newPurchaseName.isNotEmpty()) label else null,
        placeholder = {
            Text(
                modifier = Modifier.alpha(0.60f),
                text = "Name purchase",
                color = Color.White
            )
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        ),
        singleLine = true,
        trailingIcon = {
            Row {
                IconButton(
                    onClick = {
                        if (newPurchaseName.isEmpty()) return@IconButton
                        viewModel.onNewNamePurchaseChanged("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White,
                        modifier = Modifier.alpha(
                            if (newPurchaseName.isEmpty()) 0.toFloat() else 1.toFloat()
                        )
                    )
                }
                IconButton(
                    onClick = {
                        if (newPurchaseName.isEmpty()) return@IconButton
                        viewModel.insertAdd(newPurchaseName)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Done Icon",
                        tint = Color.White,
                        modifier = Modifier.alpha(
                            if (newPurchaseName.isEmpty()) 0.toFloat() else 1.toFloat()
                        )
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                if (newPurchaseName.isEmpty()) return@KeyboardActions
                viewModel.insertAdd(newPurchaseName)
            }
        ),
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