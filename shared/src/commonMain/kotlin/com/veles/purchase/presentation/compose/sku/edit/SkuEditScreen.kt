package com.veles.purchase.presentation.compose.sku.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.presentation.mvvm.sku.edit.SkuEditViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * SKU Edit Screen - create or edit shopping item
 *
 * Migrated from: SkuEditFragment.kt
 *
 * Features:
 * - Create new SKU
 * - Edit existing SKU
 * - Name field (required)
 * - Price field with currency (required)
 * - Comment field (optional)
 * - Form validation
 * - Save button
 *
 * Phase 2.14 - SKU Edit screen migration
 *
 * Simplified for KMP:
 * - Removed photo gallery (can add later)
 * - Removed date picker (can add later)
 * - Removed currency picker dialog (hardcoded for now)
 */

// Colors matching original design
object SkuEditColors {
    val colorPrimary = Color(0xFF212121)    // Toolbar
    val surface = Color(0xFF000000)         // Black background
    val textFieldBorder = Color.White.copy(alpha = 0.38f)
    val textColor = Color.White
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkuEditScreen(
    skuId: String?,
    onNavigateBack: () -> Unit = {},
    viewModel: SkuEditViewModel = koinViewModel(
        parameters = { parametersOf(skuId) }
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            SkuEditToolbar(
                title = if (skuId != null) "Edit expense" else "Add expense",
                onNavigateBack = onNavigateBack,
                onSave = {
                    viewModel.save {
                        onNavigateBack()
                    }
                },
                isSaving = uiState.isSaving
            )
        },
        containerColor = SkuEditColors.surface
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            SkuEditContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onNameChanged = viewModel::onNameChanged,
                onPriceChanged = viewModel::onPriceChanged,
                onCommentChanged = viewModel::onCommentChanged
            )
        }

        // Error snackbar
        uiState.error?.let { error ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { viewModel.clearError() }) {
                        Text("OK")
                    }
                }
            ) {
                Text(error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SkuEditToolbar(
    title: String,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit,
    isSaving: Boolean
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(
                onClick = onSave,
                enabled = !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Save",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SkuEditColors.colorPrimary
        )
    )
}

@Composable
private fun SkuEditContent(
    paddingValues: PaddingValues,
    uiState: com.veles.purchase.presentation.mvvm.sku.edit.SkuEditUiState,
    onNameChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onCommentChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Name field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.skuName,
            onValueChange = onNameChanged,
            label = {
                Text(
                    text = "Title",
                    color = SkuEditColors.textColor
                )
            },
            isError = uiState.nameError != null,
            supportingText = uiState.nameError?.let {
                {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = SkuEditColors.textColor,
                unfocusedTextColor = SkuEditColors.textColor,
                focusedBorderColor = SkuEditColors.textFieldBorder,
                unfocusedBorderColor = SkuEditColors.textFieldBorder,
                cursorColor = SkuEditColors.textColor
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Price field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.skuPrice,
            onValueChange = onPriceChanged,
            label = {
                Text(
                    text = "Price",
                    color = SkuEditColors.textColor
                )
            },
            trailingIcon = {
                Text(
                    text = uiState.skuCurrencyCode,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = SkuEditColors.textColor
                )
            },
            isError = uiState.priceError != null,
            supportingText = uiState.priceError?.let {
                {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = SkuEditColors.textColor,
                unfocusedTextColor = SkuEditColors.textColor,
                focusedBorderColor = SkuEditColors.textFieldBorder,
                unfocusedBorderColor = SkuEditColors.textFieldBorder,
                cursorColor = SkuEditColors.textColor
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Comment field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.skuComment,
            onValueChange = onCommentChanged,
            label = {
                Text(
                    text = "Comment",
                    color = SkuEditColors.textColor
                )
            },
            minLines = 3,
            maxLines = 5,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = SkuEditColors.textColor,
                unfocusedTextColor = SkuEditColors.textColor,
                focusedBorderColor = SkuEditColors.textFieldBorder,
                unfocusedBorderColor = SkuEditColors.textFieldBorder,
                cursorColor = SkuEditColors.textColor
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}