package com.veles.purchase.presentation.compose.purchase.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.presentation.mvvm.purchase.category.CategoryScreenState
import com.veles.purchase.presentation.mvvm.purchase.category.CategoryViewModel
import com.veles.purchase.presentation.mvvm.purchase.category.DialogState
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Category Management Screen
 *
 * Migrated from: CategoryFragment.kt
 *
 * Displays:
 * - List of categories with edit/delete actions
 * - FAB to add new category
 * - Edit category dialog
 * - Create category dialog
 * - Confirm leave dialog (if unsaved changes)
 *
 * Phase 2.9 - Category management migration
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    collectionId: String,
    onNavigateBack: () -> Unit = {},
    viewModel: CategoryViewModel = koinViewModel(
        parameters = { parametersOf(collectionId) }
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    // Back press handler with unsaved changes check
    val handleBackPress = {
        if (viewModel.hasChanges()) {
            viewModel.showConfirmLeaveDialog()
        } else {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            CategoryToolbar(
                onBackClicked = handleBackPress,
                onSaveClicked = {
                    scope.launch {
                        viewModel.onSaveClicked()
                        onNavigateBack()
                    }
                }
            )
        },
        floatingActionButton = {
            CategoryFAB(
                onCreateCategoryDialogClicked = viewModel::onCreateCategoryDialogClicked
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = CategoryColors.surface
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Content
            CategoryContent(
                paddingValues = paddingValues,
                categories = uiState.categories,
                onItemClicked = viewModel::onItemClicked,
                onRemoveCategory = viewModel::onRemoveCategory
            )

            // Progress overlay
            if (uiState.isLoading) {
                CategoryProgressIndicator()
            }
        }
    }

    // Dialogs
    when (val dialogState = uiState.dialogState) {
        is DialogState.EditCategoryDialog -> {
            EditCategoryDialog(
                position = dialogState.position,
                item = dialogState.item,
                onTextUpdated = viewModel::onTextUpdated,
                onDismissed = viewModel::onDialogDismissed
            )
        }
        is DialogState.CreateCategoryDialog -> {
            CreateCategoryDialog(
                onCreateCategoryClicked = viewModel::onCreateCategoryClicked,
                onDismissed = viewModel::onDialogDismissed
            )
        }
        is DialogState.ConfirmLeaveDialog -> {
            ConfirmLeaveDialog(
                onConfirmLeaveClicked = onNavigateBack,
                onDismissed = viewModel::onDialogDismissed
            )
        }
        DialogState.NoDialog -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryToolbar(
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                text = "Category settings",
                textAlign = TextAlign.Center,
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
            containerColor = CategoryColors.colorPrimary
        )
    )
}

@Composable
private fun CategoryFAB(onCreateCategoryDialogClicked: () -> Unit) {
    FloatingActionButton(
        onClick = onCreateCategoryDialogClicked,
        containerColor = CategoryColors.gr
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add Category",
            tint = Color.White
        )
    }
}

@Composable
private fun CategoryProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CategoryColors.progress)
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = CategoryColors.gr)
    }
}

@Composable
private fun CategoryContent(
    paddingValues: PaddingValues,
    categories: List<PurchaseCategoryModel>,
    onItemClicked: (Int, PurchaseCategoryModel) -> Unit,
    onRemoveCategory: (PurchaseCategoryModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues = paddingValues)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(categories) { position, item ->
                CategoryItem(
                    item = item,
                    position = position,
                    onItemClicked = onItemClicked,
                    onRemoveCategory = onRemoveCategory
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryItem(
    elevation: Dp = 4.dp,
    item: PurchaseCategoryModel,
    position: Int,
    onItemClicked: (Int, PurchaseCategoryModel) -> Unit,
    onRemoveCategory: (PurchaseCategoryModel) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = CategoryColors.colorAccent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .combinedClickable(
                onClick = { onItemClicked(position, item) }
            )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val (
                referenceTextTitle,
                referenceIconDelete,
            ) = createRefs()

            Text(
                text = item.name,
                fontSize = 18.sp,
                style = textStyle1(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .constrainAs(referenceTextTitle) {
                        start.linkTo(parent.start)
                        end.linkTo(referenceIconDelete.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )

            IconButton(
                modifier = Modifier
                    .constrainAs(referenceIconDelete) {
                        start.linkTo(referenceTextTitle.end)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                onClick = { onRemoveCategory(item) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove Category",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun CategoryNameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onImeActionDone: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = value.isEmpty(),
    placeholderText: String
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .imePadding(),
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        placeholder = {
            Text(
                modifier = Modifier.alpha(0.60f),
                text = placeholderText,
                color = Color.White
            )
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onImeActionDone() }
        ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            cursorColor = Color.White.copy(alpha = 0.60f),
            focusedIndicatorColor = CategoryColors.gr.copy(alpha = 0.87f),
            focusedLabelColor = CategoryColors.gr.copy(alpha = 0.87f)
        )
    )
}

@Composable
private fun EditCategoryDialog(
    position: Int,
    item: PurchaseCategoryModel,
    onTextUpdated: (Int, String) -> Unit,
    onDismissed: () -> Unit
) {
    var categoryName by rememberSaveable(item.id) { mutableStateOf(item.name) }

    AlertDialog(
        onDismissRequest = onDismissed,
        title = {
            Text(
                text = "Edit Category",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        },
        text = {
            Column {
                Text(
                    text = "Find the product category you need.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                CategoryNameTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    onImeActionDone = {
                        if (categoryName.isNotEmpty()) {
                            onTextUpdated(position, categoryName)
                        }
                    },
                    isError = categoryName.isEmpty(),
                    placeholderText = "Category name"
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = categoryName.isNotEmpty(),
                onClick = { onTextUpdated(position, categoryName) }
            ) {
                Text(
                    text = "Confirm",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        containerColor = CategoryColors.colorPrimaryDark
    )
}

@Composable
private fun CreateCategoryDialog(
    onCreateCategoryClicked: (String) -> Unit,
    onDismissed: () -> Unit
) {
    var categoryName by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissed,
        title = {
            Text(
                text = "Create Category",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        },
        text = {
            Column {
                Text(
                    text = "Add the product category you need.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                CategoryNameTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    onImeActionDone = {
                        if (categoryName.isNotEmpty()) {
                            onCreateCategoryClicked(categoryName)
                        }
                    },
                    isError = categoryName.isEmpty(),
                    placeholderText = "Category"
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = categoryName.isNotEmpty(),
                onClick = { onCreateCategoryClicked(categoryName) }
            ) {
                Text(
                    text = "Create",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        },
        containerColor = CategoryColors.colorPrimaryDark
    )
}

@Composable
private fun ConfirmLeaveDialog(
    onConfirmLeaveClicked: () -> Unit,
    onDismissed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissed,
        title = {
            Text(
                text = "Leave Category",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        },
        text = {
            Text(
                text = "Leave without save changes",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmLeaveClicked()
                    onDismissed()
                }
            ) {
                Text(
                    text = "Confirm",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissed) {
                Text(
                    text = "Cancel",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        containerColor = CategoryColors.colorPrimaryDark
    )
}

// Text style helper matching original
@Composable
private fun textStyle1() = TextStyle(
    color = Color.White,
    fontWeight = FontWeight.Bold
)

/**
 * Color palette for Category Screen
 * Matching original design
 */
object CategoryColors {
    val colorPrimary = Color(0xFF212121)        // Toolbar
    val colorPrimaryDark = Color(0xFF303030)    // Dialog background
    val colorAccent = Color(0xFF424242)         // Cards
    val gr = Color(0xFF4ACFAC)                  // Green accent
    val surface = Color(0xFF000000)             // Black background
    val progress = Color(0x99000000)            // Semi-transparent for overlay
}