package com.veles.purchase.presentation.compose.purchase.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Groups
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
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionEditScreen(
    collectionId: String = "", // Empty for new collection
    onNavigateBack: () -> Unit = {},
    onNavigateToCategory: (String) -> Unit = {},
    onNavigateToHistory: (String) -> Unit = {},
    onNavigateToMembers: (String, List<String>) -> Unit = { _, _ -> },
    viewModel: EditCollectionComposeViewModel = koinViewModel(
        parameters = { parametersOf(collectionId) }
    )
) {
    val progress by viewModel.flowProgress.collectAsState()
    val collectionName by viewModel.flowCollectionName.collectAsState()
    val isNameError by viewModel.flowIsNameError.collectAsState()
    val collectionModel by viewModel.flowCollectionModel.collectAsState()

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
            Content(
                paddingValues = paddingValues,
                collectionName = collectionName,
                isNameError = isNameError,
                isNewCollection = isNewCollection,
                onCollectionNameChange = viewModel::onCollectionNameChange,
                onCategoryClicked = { onNavigateToCategory(collectionModel.id) },
                onHistoryClicked = { onNavigateToHistory(collectionModel.id) },
                onMembersClicked = {
                    onNavigateToMembers(collectionModel.id, collectionModel.listMembers)
                }
            )

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
    onHistoryClicked: () -> Unit,
    onMembersClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ComponentName(
                    collectionName = collectionName,
                    isError = isNameError,
                    onNameChange = onCollectionNameChange
                )
            }

            item {
                ComponentCategory(onCategoryClicked)
            }

            item {
                ComponentMembers(onMembersClicked)
            }

            if (!isNewCollection) {
                item {
                    ComponentHistory(onHistoryClicked)
                }
            }
        }
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
        modifier = Modifier.fillMaxWidth(),
        isError = isError,
        value = collectionName,
        onValueChange = onNameChange,
        label = {
            Text(text = "Title", color = Color.White.copy(alpha = 0.7f))
        },
        singleLine = true
    )
}

@Composable
private fun ComponentCategory(onCategoryClicked: () -> Unit) {
    MenuCard(
        icon = {
            Icon(
                painter = painterResource(Res.drawable.ic_category),
                contentDescription = "Category",
                tint = Colors.gr,
                modifier = Modifier.size(24.dp)
            )
        },
        title = "Category settings",
        onClick = onCategoryClicked
    )
}

@Composable
private fun ComponentMembers(onMembersClicked: () -> Unit) {
    MenuCard(
        icon = {
            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = "Members",
                tint = Colors.gr,
                modifier = Modifier.size(24.dp)
            )
        },
        title = "Members",
        onClick = onMembersClicked
    )
}

@Composable
private fun ComponentHistory(onHistoryClicked: () -> Unit) {
    MenuCard(
        icon = {
            Icon(
                painter = painterResource(Res.drawable.ic_baseline_history_24),
                contentDescription = "History",
                tint = Colors.gr,
                modifier = Modifier.size(24.dp)
            )
        },
        title = "History",
        onClick = onHistoryClicked
    )
}

@Composable
private fun MenuCard(
    icon: @Composable () -> Unit,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Colors.colorAccent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                text = title,
                color = Color.White
            )
            Icon(
                painter = painterResource(Res.drawable.ic_navigate_next),
                contentDescription = "Next",
                tint = Color.White.copy(alpha = 0.5f),
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
    focusedBorderColor = Colors.gr,
    unfocusedBorderColor = Color.White.copy(alpha = 0.38f),
    errorBorderColor = Color.Red.copy(alpha = 0.7f),
    focusedLabelColor = Colors.gr,
    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
    errorLabelColor = Color.Red.copy(alpha = 0.7f),
    cursorColor = Colors.gr
)
