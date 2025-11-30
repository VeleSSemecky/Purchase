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
import androidx.compose.material.icons.filled.*
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
 * - Search functionality
 * - Swipe to delete
 * - Add new purchase
 * - Category chips (if available)
 * - Photo indicators (if available)
 *
 * Phase 2.6 - Purchase List feature migration (simplified)
 */

// Colors matching original design
object PurchaseListColors {
    val colorPrimary = Color(0xff212121)
    val colorAccent = Color(0xff424242)  // Card background
    val gr = Color(0xff4ACFAC)  // Green accent
    val surface = Color(0xFF000000)  // Black background
    val progress = Color(0x99000000)
}

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
    var searchWidgetState by remember { mutableStateOf(SearchWidgetState.CLOSED) }

    Scaffold(
        containerColor = PurchaseListColors.surface,
        topBar = {
            when (searchWidgetState) {
                SearchWidgetState.CLOSED -> {
                    PurchaseListTopBar(
                        collectionName = collection.name,
                        onBackClick = onNavigateBack,
                        onSearchClick = { searchWidgetState = SearchWidgetState.OPENED },
                        onSettingsClick = onNavigateToSettings
                    )
                }
                SearchWidgetState.OPENED -> {
                    SearchTopBar(
                        searchText = searchText,
                        onTextChange = { viewModel.updateSearchText(it) },
                        onCloseClick = {
                            searchWidgetState = SearchWidgetState.CLOSED
                            viewModel.updateSearchText("")
                        }
                    )
                }
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PurchaseListTopBar(
    collectionName: String,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                text = collectionName.ifEmpty { "Purchases" },
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PurchaseListColors.colorPrimary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar(
    searchText: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = onTextChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text("Search purchases...", color = Color.Gray)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        },
        trailingIcon = {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = PurchaseListColors.colorPrimary,
            unfocusedContainerColor = PurchaseListColors.colorPrimary,
            cursorColor = PurchaseListColors.gr,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun Progress(viewModel: PurchaseListViewModel) {
    val progress by viewModel.flowProgress.collectAsState()
    if (progress != PurchaseListViewModel.ProgressState.Start) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurchaseListColors.progress)
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PurchaseListColors.gr)
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

        // Purchase list
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = sortedPurchases,
                key = { it.createId }
            ) { purchase ->
                val dismissState = rememberSwipeToDismissBoxState()

                if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart ||
                    dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd
                ) {
                    LaunchedEffect(purchase) {
                        viewModel.deletePurchase(purchase)
                        dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                    }
                }

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {}
                ) {
                    val elevation = animateDpAsState(
                        if (dismissState.targetValue != SwipeToDismissBoxValue.Settled) 4.dp else 0.dp
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
        color = PurchaseListColors.gr,
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
            containerColor = PurchaseListColors.colorAccent
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
                        checkedColor = PurchaseListColors.gr,
                        uncheckedColor = PurchaseListColors.gr,
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
                color = PurchaseListColors.gr.copy(alpha = 0.1f),
                shape = CircleShape
            )
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "📷",
            fontSize = 12.sp,
            color = PurchaseListColors.gr,
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
                color = PurchaseListColors.gr.copy(alpha = 0.1f),
                shape = CircleShape
            )
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.purchaseCategoryModel?.name ?: "Uncategorized",
            fontSize = 12.sp,
            style = TextStyle(
                color = PurchaseListColors.gr,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
        )
    }
}

// Text style functions matching original
@Composable
private fun textStyle1() = TextStyle(
    color = Color.White,
    fontWeight = FontWeight.Bold
)

@Composable
private fun textStyle2() = TextStyle(
    color = Color.White.copy(alpha = 0.6f)
)

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
            focusedIndicatorColor = PurchaseListColors.gr.copy(alpha = 0.87f),
            focusedLabelColor = PurchaseListColors.gr.copy(alpha = 0.87f)
        )
    )
}

/**
 * Search widget state
 */
enum class SearchWidgetState {
    OPENED,
    CLOSED
}