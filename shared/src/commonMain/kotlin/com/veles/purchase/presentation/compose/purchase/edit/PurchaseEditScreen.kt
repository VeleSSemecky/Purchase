package com.veles.purchase.presentation.compose.purchase.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.mvvm.purchase.edit.EditPurchaseViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Purchase Edit/Add Screen - Modernized version
 * 
 * Improvements:
 * - Single UI State subscription
 * - Keyboard navigation (ImeAction.Next/Done)
 * - Auto-focus for new purchases
 * - ModalBottomSheet for category selection
 * - Modularized components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseEditScreen(
    collectionId: String,
    purchaseId: String = "",
    onNavigateBack: () -> Unit = {},
    viewModel: EditPurchaseViewModel = koinViewModel(
        parameters = { parametersOf(collectionId, purchaseId) }
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var showCategorySheet by remember { mutableStateOf(false) }

    // Focus management
    val titleFocusRequester = remember { FocusRequester() }
    val priceFocusRequester = remember { FocusRequester() }
    val commentFocusRequester = remember { FocusRequester() }

    // Stabilized callbacks
    val onTitleChange = remember(viewModel) { { text: String -> viewModel.onTitleChange(text) } }
    val onPriceChange = remember(viewModel) { { text: String -> viewModel.onPriceChange(text) } }
    val onCommentChange = remember(viewModel) { { text: String -> viewModel.onCommentChange(text) } }
    val onCheckedChange = remember(viewModel) { { checked: Boolean -> viewModel.onCheckedChange(checked) } }
    val onCategorySelected = remember(viewModel) { { category: com.veles.purchase.domain.model.purchase.PurchaseCategoryModel? -> viewModel.onCategorySelected(category) } }
    
    val onSaveClicked = remember(viewModel, onNavigateBack) {
        {
            scope.launch {
                val success = viewModel.onSaveClicked()
                if (success) {
                    onNavigateBack()
                }
            }
        }
    }

    val isSaveEnabled by remember {
        derivedStateOf { uiState.purchase.text.isNotBlank() }
    }
    
    val isLoading by remember {
        derivedStateOf { uiState.progress == EditPurchaseViewModel.ProgressState.Start }
    }

    // Auto-focus on Title for new purchases
    LaunchedEffect(Unit) {
        if (uiState.isNewPurchase) {
            titleFocusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            PurchaseEditToolbar(
                title = if (uiState.isNewPurchase) "Add Purchase" else "Edit Purchase",
                onNavigateBack = onNavigateBack,
                onSaveClicked = { onSaveClicked() },
                isSaveEnabled = isSaveEnabled,
                isLoading = isLoading
            )
        },
        containerColor = Colors.surface
    ) { paddingValues ->
        PurchaseEditForm(
            modifier = Modifier.padding(paddingValues),
            purchase = uiState.purchase,
            onTitleChange = onTitleChange,
            onPriceChange = onPriceChange,
            onCommentChange = onCommentChange,
            onCheckedChange = onCheckedChange,
            onShowCategorySheet = { showCategorySheet = true },
            titleFocusRequester = titleFocusRequester,
            priceFocusRequester = priceFocusRequester,
            commentFocusRequester = commentFocusRequester
        )

        if (isLoading) {
            LoadingDialog()
        }
    }

    if (showCategorySheet) {
        CategoryBottomSheet(
            categories = uiState.categories,
            selectedCategory = uiState.purchase.purchaseCategoryModel,
            onCategorySelected = onCategorySelected,
            onDismiss = { showCategorySheet = false }
        )
    }
}

@Composable
private fun PurchaseEditForm(
    purchase: com.veles.purchase.domain.model.purchase.PurchaseModel,
    onTitleChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onShowCategorySheet: () -> Unit,
    titleFocusRequester: FocusRequester,
    priceFocusRequester: FocusRequester,
    commentFocusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Title field
        EditTextField(
            value = purchase.text,
            onValueChange = onTitleChange,
            label = "Title",
            isError = purchase.text.isBlank() && purchase.text.isNotEmpty(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.focusRequester(titleFocusRequester)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Price field
        EditTextField(
            value = purchase.price,
            onValueChange = { input ->
                // Basic validation for numbers
                if (input.isEmpty() || input.matches("[0123456789.]*".toRegex())) {
                    onPriceChange(input)
                }
            },
            label = "Price",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                Text(
                    text = "$",
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(end = 16.dp)
                )
            },
            modifier = Modifier.focusRequester(priceFocusRequester)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Comment field
        EditTextField(
            value = purchase.count,
            onValueChange = onCommentChange,
            label = "Comment",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.focusRequester(commentFocusRequester)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Checked toggle
        CheckedCard(
            checked = purchase.isChecked,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category picker
        CategorySelector(
            selectedCategory = purchase.purchaseCategoryModel,
            onClick = onShowCategorySheet
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}
