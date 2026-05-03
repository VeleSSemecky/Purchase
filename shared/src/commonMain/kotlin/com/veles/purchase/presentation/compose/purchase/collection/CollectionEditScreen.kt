package com.veles.purchase.presentation.compose.purchase.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.mvvm.purchase.collection.EditCollectionComposeViewModel
import com.veles.purchase.shared.resources.Res
import com.veles.purchase.shared.resources.ic_baseline_history_24
import com.veles.purchase.shared.resources.ic_category
import com.veles.purchase.shared.resources.ic_navigate_next
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Collection Edit/Add Screen
 *
 * Migrated from: EditCollectionComposeFragment.kt
 *
 * Displays:
 * - Form fields: collection name
 * - Category settings button (navigate to category screen)
 * - History button (navigate to history screen)
 * - Save button in toolbar
 * - Loading indicator during save
 *
 * Phase 2.8 - Simplified version (no user selection list)
 * FIXED: Now using custom components matching pattern exactly
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionEditScreen(
    collectionId: String = "", // Empty for new collection
    onNavigateBack: () -> Unit = {},
    onNavigateToCategory: (String) -> Unit = {}, // TODO: Navigate to category screen
    onNavigateToHistory: (String) -> Unit = {}, // TODO: Navigate to history screen
    viewModel: EditCollectionComposeViewModel = koinViewModel(
        parameters = { parametersOf(collectionId) }
    )
) {
    val progress by viewModel.flowProgress.collectAsState()
    val collectionName by viewModel.flowCollectionName.collectAsState()
    val isNameError by viewModel.flowIsNameError.collectAsState()

    val scope = rememberCoroutineScope()

    val isNewCollection = viewModel.isNewCollection

    Scaffold(
        topBar = {
            ToolBar(
                title = if (isNewCollection) "Create Collection" else "Edit Collection",
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
            Content(
                paddingValues = paddingValues,
                collectionName = collectionName,
                isNameError = isNameError,
                isNewCollection = isNewCollection,
                onCollectionNameChange = viewModel::onCollectionNameChange,
                onCategoryClicked = { onNavigateToCategory(collectionId) },
                onHistoryClicked = { onNavigateToHistory(collectionId) }
            )

            // Progress overlay
            if (progress == EditCollectionComposeViewModel.ProgressState.Start) {
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
private fun ToolBar(
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
            containerColor = Colors.colorPrimary
        )
    )
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    collectionName: String,
    isNameError: Boolean,
    isNewCollection: Boolean,
    onCollectionNameChange: (String) -> Unit,
    onCategoryClicked: () -> Unit,
    onHistoryClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues = paddingValues)
    ) {
        Spacer(modifier = Modifier.padding(16.dp))

        // Collection Name Field
        ComponentName(
            collectionName = collectionName,
            isError = isNameError,
            onNameChange = onCollectionNameChange
        )

        Spacer(modifier = Modifier.padding(8.dp))

        // Category Settings Button (always visible)
        ComponentCategory(onCategoryClicked)

        // History Button — only visible when editing an existing collection
        if (!isNewCollection) {
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentHistory(onHistoryClicked)
        }

        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Composable
private fun ComponentName(
    collectionName: String,
    isError: Boolean,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        colors = collectionEditTextFieldColors(),
        textStyle = textStyle(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp
            ),
        isError = isError,
        value = collectionName,
        onValueChange = { onNameChange(it) },
        label = {
            Text(
                text = "Title",
                color = Color.White
            )
        }
    )
}

@Composable
private fun ComponentCategory(onCategoryClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp)
            .clickable { onCategoryClicked() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = Colors.colorAccent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_category),
                contentDescription = "Category",
                tint = Colors.gr,
                modifier = Modifier.size(24.dp)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                text = "Category settings",
                color = Color.White
            )
            Icon(
                painter = painterResource(Res.drawable.ic_navigate_next),
                contentDescription = "Next",
                tint = Colors.gr,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ComponentHistory(onHistoryClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp)
            .clickable { onHistoryClicked() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = Colors.colorAccent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_baseline_history_24),
                contentDescription = "History",
                tint = Colors.gr,
                modifier = Modifier.size(24.dp)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                text = "History",
                color = Color.White
            )
            Icon(
                painter = painterResource(Res.drawable.ic_navigate_next),
                contentDescription = "Next",
                tint = Colors.gr,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun textStyle() = TextStyle(
    color = Color.White
)

@Composable
private fun collectionEditTextFieldColors() = OutlinedTextFieldDefaults.colors(
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
