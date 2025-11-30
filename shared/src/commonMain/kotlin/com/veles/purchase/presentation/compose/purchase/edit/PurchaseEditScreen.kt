package com.veles.purchase.presentation.compose.purchase.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.mvvm.purchase.edit.PurchaseEditViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Purchase Edit/Add Screen
 *
 * Migrated from: /presentation/.../mvvm/purchase/edit/EditPurchaseFragment.kt
 *
 * Displays:
 * - Form fields: title, price, comment, checked toggle, category
 * - Save button in toolbar
 * - Loading indicator during save
 *
 * Phase 2.7 - Simplified version (no photos, no date picker)
 * FIXED: Now using custom components matching pattern exactly
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseEditScreen(
    collectionId: String,
    purchaseId: String = "", // Empty for new purchase
    onNavigateBack: () -> Unit = {},
    viewModel: PurchaseEditViewModel = koinViewModel(
        parameters = { parametersOf(collectionId, purchaseId) }
    )
) {
    val progress by viewModel.flowProgress.collectAsState()
    val purchaseName by viewModel.flowPurchaseName.collectAsState()
    val purchasePrice by viewModel.flowPurchasePrice.collectAsState()
    val purchaseComment by viewModel.flowPurchaseComment.collectAsState()
    val purchaseIsChecked by viewModel.flowPurchaseIsChecked.collectAsState()
    val purchaseCategory by viewModel.flowPurchaseCategory.collectAsState()
    val categories by viewModel.flowCategories.collectAsState()

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            PurchaseEditToolbar(
                title = if (viewModel.isNewPurchase) "Add Purchase" else "Edit Purchase",
                onNavigateBack = onNavigateBack,
                onSaveClicked = {
                    scope.launch {
                        val success = viewModel.onSaveClicked()
                        if (success) {
                            onNavigateBack()
                        }
                    }
                }
            )
        },
        containerColor = Colors.surface
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Content
            PurchaseEditContent(
                paddingValues = paddingValues,
                purchaseName = purchaseName,
                purchasePrice = purchasePrice,
                purchaseComment = purchaseComment,
                purchaseIsChecked = purchaseIsChecked,
                purchaseCategory = purchaseCategory,
                categories = categories,
                onNameChange = viewModel::onTitleChange,
                onPriceChange = viewModel::onPriceChange,
                onCommentChange = viewModel::onCommentChange,
                onCheckedChange = viewModel::onCheckedChange,
                onCategorySelected = viewModel::onCategorySelected
            )

            // Progress overlay
            if (progress == PurchaseEditViewModel.ProgressState.Start) {
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PurchaseEditToolbar(
    title: String,
    onNavigateBack: () -> Unit,
    onSaveClicked: () -> Unit
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
            Text(
                text = title,
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = onSaveClicked) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Save",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.colorPrimary
        )
    )
}

@Composable
private fun PurchaseEditContent(
    paddingValues: PaddingValues,
    purchaseName: String,
    purchasePrice: String,
    purchaseComment: String,
    purchaseIsChecked: Boolean,
    purchaseCategory: PurchaseCategoryModel?,
    categories: List<PurchaseCategoryModel>,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onCategorySelected: (PurchaseCategoryModel?) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Title field
        TitleField(value = purchaseName, onValueChange = onNameChange)

        Spacer(modifier = Modifier.height(8.dp))

        // Price field
        PriceField(value = purchasePrice, onValueChange = onPriceChange)

        Spacer(modifier = Modifier.height(8.dp))

        // Comment field
        CommentField(value = purchaseComment, onValueChange = onCommentChange)

        Spacer(modifier = Modifier.height(8.dp))

        // Checked toggle
        CheckedSwitch(checked = purchaseIsChecked, onCheckedChange = onCheckedChange)

        Spacer(modifier = Modifier.height(8.dp))

        // Category picker
        CategoryPicker(
            selectedCategory = purchaseCategory,
            categories = categories,
            onCategorySelected = onCategorySelected
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TitleField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        colors = purchaseEditTextFieldColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp
            ),
        isError = value.isBlank(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(
                text = "Title",
                color = Color.White
            )
        }
    )
}

@Composable
private fun PriceField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        colors = purchaseEditTextFieldColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp
            ),
        value = value,
        maxLines = 1,
        trailingIcon = {
            Text(
                modifier = Modifier.padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
                text = "$",  // Default currency for Phase 2.7
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.White
            )
        },
        onValueChange = { it ->
            if (it.count { it.toString() == "." } > 1 ||
                it.substringAfter(".", emptyString()).count() > 2
            ) {
                return@OutlinedTextField
            }
            if (it.isEmpty() || it.matches("[0123456789.]+".toRegex())) {
                onValueChange(it)
            }
        },
        label = {
            Text(
                "Price",
                color = Color.White
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun CommentField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        colors = purchaseEditTextFieldColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp
            ),
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text("Comment", color = Color.White) }
    )
}

@Composable
private fun CheckedSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(PaddingValues(horizontal = 20.dp))
            .border(
                width = 1.dp,
                Color.White.copy(alpha = 0.38f),
                shape = OutlinedTextFieldDefaults.shape
            )
            .padding(PaddingValues(horizontal = 16.dp))
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Checked",
            color = Color.White
        )
        Switch(
            modifier = Modifier,
            checked = checked,
            onCheckedChange = { isChecked ->
                onCheckedChange(isChecked)
            }
        )
    }
}

@Composable
private fun CategoryPicker(
    selectedCategory: PurchaseCategoryModel?,
    categories: List<PurchaseCategoryModel>,
    onCategorySelected: (PurchaseCategoryModel?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        OutlinedTextField(
            colors = purchaseEditTextFieldColors().copy(
                disabledIndicatorColor = Color.White.copy(alpha = 0.38f),
                disabledTextColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (categories.isNotEmpty()) {
                        expanded = !expanded
                    }
                },
            enabled = false,
            readOnly = true,
            value = selectedCategory?.name ?: "",
            onValueChange = {},
            label = {
                Text(
                    text = "Category",
                    color = Color.White
                )
            },
            trailingIcon = {
                if (categories.isNotEmpty()) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Expand",
                        tint = Color.White
                    )
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            // Option to clear category
            if (selectedCategory != null) {
                DropdownMenuItem(
                    text = { Text("None", color = Color.White) },
                    onClick = {
                        onCategorySelected(null)
                        expanded = false
                    }
                )
                HorizontalDivider()
            }

            // Available categories
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name, color = Color.White) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }

            if (categories.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No categories available", color = Color.Gray) },
                    onClick = { expanded = false },
                    enabled = false
                )
            }
        }
    }
}

@Composable
private fun purchaseEditTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    disabledTextColor = Color.White.copy(alpha = 0.38f),
    errorTextColor = Color.White,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color.White.copy(alpha = 0.38f),
    errorBorderColor = Color.Red.copy(alpha = 0.7f),
    focusedLabelColor = Color.White,
    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
    errorLabelColor = Color.Red.copy(alpha = 0.7f),
    cursorColor = Color.White
)