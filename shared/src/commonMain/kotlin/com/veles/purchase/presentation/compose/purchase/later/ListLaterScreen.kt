package com.veles.purchase.presentation.compose.purchase.later

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.presentation.mvvm.purchase.later.ListLaterViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * List Later Purchases Screen
 *
 * Migrated from: ListLaterPurchaseFragment.kt
 *
 * Features:
 * - Shows "later" purchases (items to buy in the future)
 * - Create new later purchases inline
 * - Check/uncheck purchases
 * - Swipe to delete
 * - Search functionality
 *
 * Phase 2.12 - List Later screen migration
 */

// Colors matching original design
object ListLaterColors {
    val colorPrimary = Color(0xFF212121)    // Toolbar
    val colorAccent = Color(0xFF424242)     // Cards
    val gr = Color(0xFF4ACFAC)              // Green accent
    val surface = Color(0xFF000000)         // Black background
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListLaterScreen(
    collectionId: String,
    onNavigateBack: () -> Unit = {},
    onNavigateToPurchaseEdit: (String) -> Unit = {},
    viewModel: ListLaterViewModel = koinViewModel(
        parameters = { parametersOf(collectionId) }
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            ListLaterToolbar(
                collectionName = uiState.collection.name,
                onNavigateBack = onNavigateBack
            )
        },
        containerColor = ListLaterColors.surface
    ) { paddingValues ->
        ListLaterContent(
            paddingValues = paddingValues,
            uiState = uiState,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
            onPurchaseClick = { purchase ->
                onNavigateToPurchaseEdit(purchase.createId)
            },
            onPurchaseCheck = viewModel::onPurchaseCheck,
            onDeletePurchase = viewModel::onDeletePurchase,
            onNewPurchaseNameChanged = viewModel::onNewPurchaseNameChanged,
            onCreatePurchase = viewModel::onCreatePurchase,
            onClearNewPurchaseName = viewModel::onClearNewPurchaseName
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListLaterToolbar(
    collectionName: String,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        title = {
            Column {
                Text(
                    text = collectionName,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Buy Later",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = ListLaterColors.gr,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ListLaterColors.colorPrimary
        )
    )
}

@Composable
private fun ListLaterContent(
    paddingValues: PaddingValues,
    uiState: com.veles.purchase.presentation.mvvm.purchase.later.ListLaterUiState,
    onSearchQueryChanged: (String) -> Unit,
    onPurchaseClick: (PurchaseModel) -> Unit,
    onPurchaseCheck: (PurchaseModel) -> Unit,
    onDeletePurchase: (PurchaseModel) -> Unit,
    onNewPurchaseNameChanged: (String) -> Unit,
    onCreatePurchase: (String) -> Unit,
    onClearNewPurchaseName: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        // Info text
        Text(
            text = "Items to buy later (not urgently)",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(16.dp)
        )

        // Purchase list
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            if (uiState.filteredPurchases.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No later purchases\nAdd items you want to buy in the future",
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(
                    items = uiState.filteredPurchases,
                    key = { it.createId }
                ) { purchase ->
                    val dismissState = rememberSwipeToDismissBoxState()

                    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart ||
                        dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd
                    ) {
                        LaunchedEffect(purchase.createId) {
                            onDeletePurchase(purchase)
                            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                        }
                    }

                    SwipeToDismissBox(
                        modifier = Modifier.fillMaxWidth(),
                        state = dismissState,
                        backgroundContent = {}
                    ) {
                        val elevation = animateDpAsState(
                            if (dismissState.targetValue != SwipeToDismissBoxValue.Settled) 4.dp else 0.dp,
                            label = "elevation"
                        ).value

                        PurchaseItem(
                            purchase = purchase,
                            setting = uiState.setting,
                            elevation = elevation,
                            onClick = { onPurchaseClick(purchase) },
                            onCheck = { onPurchaseCheck(purchase) }
                        )
                    }
                }
            }
        }

        // Create new purchase
        CreatePurchaseField(
            value = uiState.newPurchaseName,
            onValueChange = onNewPurchaseNameChanged,
            onClear = onClearNewPurchaseName,
            onCreate = onCreatePurchase
        )
    }
}

@Composable
private fun PurchaseItem(
    purchase: PurchaseModel,
    setting: com.veles.purchase.domain.model.setting.PurchaseSetting,
    elevation: Dp,
    onClick: () -> Unit,
    onCheck: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = ListLaterColors.colorAccent
        ),
        shape = when (setting.shapeType) {
            com.veles.purchase.domain.model.setting.ShapeType.ROUNDED -> RoundedCornerShape(8.dp)
            com.veles.purchase.domain.model.setting.ShapeType.CUT -> RoundedCornerShape(0.dp)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (iconPhoto, textTitle, checkbox) = createRefs()

            // Photo indicator
            if (setting.isImage) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .constrainAs(iconPhoto) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Text(
                        text = if (purchase.listImage.isNotEmpty()) "📷" else "  ",
                        fontSize = 24.sp,
                        color = ListLaterColors.gr
                    )
                }
            }

            // Purchase name
            Text(
                text = purchase.text,
                fontSize = 18.sp,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(textTitle) {
                        start.linkTo(if (setting.isImage) iconPhoto.end else parent.start, margin = if (setting.isImage) 0.dp else 16.dp)
                        end.linkTo(checkbox.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )

            // Checkbox
            Checkbox(
                checked = purchase.isChecked,
                onCheckedChange = { onCheck() },
                colors = CheckboxDefaults.colors(
                    checkedColor = ListLaterColors.gr,
                    uncheckedColor = ListLaterColors.gr,
                    checkmarkColor = Color.Black
                ),
                modifier = Modifier.constrainAs(checkbox) {
                    start.linkTo(textTitle.end)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}

@Composable
private fun CreatePurchaseField(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    onCreate: (String) -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                modifier = Modifier.alpha(alpha = 0.38f),
                text = "Add item to buy later...",
                color = Color.White
            )
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = Color.White
        ),
        singleLine = true,
        trailingIcon = {
            Row {
                IconButton(
                    onClick = {
                        if (value.isEmpty()) return@IconButton
                        onClear()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Color.White,
                        modifier = Modifier.alpha(
                            if (value.isEmpty()) 0f else 1f
                        )
                    )
                }
                IconButton(
                    onClick = {
                        if (value.isEmpty()) return@IconButton
                        onCreate(value)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.alpha(
                            if (value.isEmpty()) 0f else 1f
                        )
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (value.isEmpty()) return@KeyboardActions
                onCreate(value)
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.White.copy(alpha = 0.38f),
            focusedIndicatorColor = ListLaterColors.gr.copy(alpha = 0.38f),
            focusedLabelColor = ListLaterColors.gr.copy(alpha = 0.38f)
        )
    )
}