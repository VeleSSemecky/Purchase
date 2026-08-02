package com.veles.purchase.presentation.compose.sku.scanner

import com.veles.purchase.domain.utill.formatAmount
import com.veles.purchase.domain.model.setting.AiEngineStrategy
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.veles.purchase.domain.model.scanner.ReceiptItem
import com.veles.purchase.platform.media.rememberCameraLauncher
import com.veles.purchase.platform.media.rememberMediaPickerLauncher
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.model.UiEvent
import com.veles.purchase.presentation.mvvm.sku.scanner.ReceiptScannerState
import com.veles.purchase.presentation.mvvm.sku.scanner.ReceiptScannerViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScannerScreen(
    onNavigateBack: () -> Unit = {},
    onConfirm: () -> Unit = {},
    viewModel: ReceiptScannerViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentOnNavigateBack by rememberUpdatedState(onNavigateBack)
    val currentOnConfirm by rememberUpdatedState(onConfirm)

    var showEngineSelection by remember { mutableStateOf(false) }

    val launchCamera = rememberCameraLauncher { bytes -> viewModel.onImageSelected(bytes) }
    val launchGallery = rememberMediaPickerLauncher { bytes -> viewModel.onImageSelected(bytes) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.ShowError -> snackbarHostState.showSnackbar(event.message)
                is UiEvent.NavigateBack -> currentOnNavigateBack()
            }
        }
    }

    if (showEngineSelection) {
        ModalBottomSheet(onDismissRequest = { showEngineSelection = false }) {
            EngineSelectionContent(
                onSelectEngine = {
                    viewModel.onSelectEngine(it)
                    showEngineSelection = false
                },
                onDownloadLocal = {
                    viewModel.startLocalModelDownload()
                    showEngineSelection = false
                }
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Colors.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.colorPrimary)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = currentOnNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Scan Receipt",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showEngineSelection = true }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Change Engine",
                        tint = Color.White
                    )
                }
            }

            // Key on state type only — selection changes won't re-trigger the animation
            AnimatedContent(
                targetState = state,
                contentKey = { it::class },
                label = "receipt_scanner_phase"
            ) { currentState ->
                when (currentState) {
                    is ReceiptScannerState.Idle -> {
                        IdleContent(
                            onOpenCamera = { launchCamera() },
                            onOpenGallery = { launchGallery() }
                        )
                    }
                    is ReceiptScannerState.Processing -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(color = Colors.gr)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Scanning receipt...", color = Color.White)
                            }
                        }
                    }
                    is ReceiptScannerState.AiUnavailable -> {
                        EngineSelectionContent(
                            onSelectEngine = viewModel::onSelectEngine,
                            onDownloadLocal = viewModel::startLocalModelDownload
                        )
                    }
                    is ReceiptScannerState.EngineSelectionRequired -> {
                        EngineSelectionContent(
                            onSelectEngine = viewModel::onSelectEngine,
                            onDownloadLocal = viewModel::startLocalModelDownload
                        )
                    }
                    is ReceiptScannerState.ModelDownloading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(progress = { currentState.progress }, color = Colors.gr)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Downloading local model...", color = Color.White)
                            }
                        }
                    }
                    is ReceiptScannerState.Result -> {
                        // Read selection separately — changes here do NOT affect AnimatedContent
                        val selectedIndices by viewModel.selectedIndices.collectAsState()
                        ResultContent(
                            resultState = currentState,
                            selectedIndices = selectedIndices,
                            onToggleItem = viewModel::onToggleItem,
                            onEditPrice = viewModel::onEditItemPrice,
                            onConfirm = {
                                viewModel.prepareHandoff()
                                currentOnConfirm()
                            },
                            onRetry = viewModel::onRetry
                        )
                    }
                    is ReceiptScannerState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Text(
                                    text = "Scanning failed",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = currentState.message,
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(
                                    onClick = viewModel::onRetry,
                                    colors = ButtonDefaults.buttonColors(containerColor = Colors.gr)
                                ) {
                                    Icon(Icons.Default.Refresh, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Try Again")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun IdleContent(
    onOpenCamera: () -> Unit,
    onOpenGallery: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "🧾",
                fontSize = 64.sp
            )
            Text(
                text = "Scan a receipt to automatically\nextract expenses",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onOpenCamera,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.gr)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Take Photo", color = Color.Black)
            }
            OutlinedButton(
                onClick = onOpenGallery,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Colors.gr)
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Choose from Gallery")
            }
        }
    }
}

@Composable
private fun EngineSelectionContent(
    onSelectEngine: (AiEngineStrategy) -> Unit,
    onDownloadLocal: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(text = "🤖", fontSize = 56.sp)
            Text(
                text = "Choose AI Engine",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Choose how to parse the selected receipt image:",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = { onSelectEngine(AiEngineStrategy.GROQ_CLOUD) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.gr)
            ) {
                Text("🌐  Groq Vision (Fast, requires Internet)", color = Color.Black)
            }
            Button(
                onClick = { onSelectEngine(AiEngineStrategy.OCR_GROQ) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.gr)
            ) {
                Text("🔍  OCR + Groq 70B (Best quality, requires Internet)", color = Color.Black)
            }
            Button(
                onClick = { onSelectEngine(AiEngineStrategy.OCR_TEXT_LLM) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.gr)
            ) {
                Text("📄  OCR + Text LLM (Offline)", color = Color.Black)
            }
            OutlinedButton(
                onClick = onDownloadLocal,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Colors.gr)
            ) {
                Text("💾  Download offline model (~1 GB)")
            }
        }
    }
}

@Composable
private fun ResultContent(
    resultState: ReceiptScannerState.Result,
    selectedIndices: Set<Int>,
    onToggleItem: (Int) -> Unit,
    onEditPrice: (Int, Double) -> Unit,
    onConfirm: () -> Unit,
    onRetry: () -> Unit
) {
    val data = resultState.data
    var imageExpanded by remember { mutableStateOf(true) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }

    editingIndex?.let { index ->
        val item = data.items[index]
        EditPriceDialog(
            itemName = item.name,
            currentPrice = item.price,
            currency = data.currency,
            onConfirm = { newPrice ->
                onEditPrice(index, newPrice)
                editingIndex = null
            },
            onDismiss = { editingIndex = null }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        if (resultState.imageBytes.isNotEmpty()) {
            item(key = "receipt_image") {
                ReceiptImagePreview(
                    imageBytes = resultState.imageBytes,
                    expanded = imageExpanded,
                    onToggle = { imageExpanded = !imageExpanded }
                )
            }
        }

        // Total summary card
        if (data.totalAmount != null || data.items.isNotEmpty()) {
            item(key = "summary_row") {
                SummaryRow(
                    total = data.totalAmount,
                    itemCount = data.items.size,
                    selectedCount = selectedIndices.size,
                    currency = data.currency
                )
            }
        }

        if (data.items.isNotEmpty()) {
            item(key = "items_header") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Items (${data.items.size})",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Tap to select",
                        color = Color.Gray.copy(alpha = 0.6f),
                        fontSize = 11.sp
                    )
                }
            }
            itemsIndexed(
                items = data.items,
                key = { index, item -> "${index}_${item.name}" }
            ) { index, item ->
                val isSelected = index in selectedIndices
                ReceiptItemRow(
                    item = item,
                    currency = data.currency,
                    isSelected = isSelected,
                    onToggle = remember(index) { { onToggleItem(index) } },
                    onEditPrice = remember(index) { { editingIndex = index } }
                )
            }
        } else if (data.totalAmount == null) {
            item(key = "empty_state") {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Nothing recognized", color = Color.Gray, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(onClick = onRetry) {
                            Icon(Icons.Default.Refresh, null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Retry")
                        }
                    }
                }
            }
        }

        // Single primary action button
        item(key = "save_button") {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.gr)
            ) {
                Icon(Icons.Default.Done, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                val label = when {
                    selectedIndices.isNotEmpty() -> "Save expense (${selectedIndices.size} items)"
                    data.items.isNotEmpty() -> "Save expense (${data.items.size} items)"
                    data.totalAmount != null -> "Save expense"
                    else -> "Save expense"
                }
                Text(label, color = Color.Black, fontWeight = FontWeight.SemiBold)
            }
            TextButton(
                onClick = onRetry,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(Icons.Default.Refresh, null, tint = Colors.gr)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Scan again", color = Colors.gr)
            }
        }
    }
}

@Composable
private fun ReceiptImagePreview(
    imageBytes: ByteArray,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.colorPrimary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onToggle)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Receipt photo",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = Color.Gray
                )
            }
            AnimatedVisibility(visible = expanded) {
                AsyncImage(
                    model = imageBytes,
                    contentDescription = "Scanned receipt",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 320.dp)
                )
            }
        }
    }
}

@Composable
private fun EditPriceDialog(
    itemName: String,
    currentPrice: Double,
    currency: String,
    onConfirm: (Double) -> Unit,
    onDismiss: () -> Unit
) {
    var priceText by remember { mutableStateOf(currentPrice.formatAmount()) }
    val isValid = priceText.replace(",", ".").toDoubleOrNull() != null

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Colors.surface,
        title = {
            Text(
                text = "Edit price",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = itemName,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    maxLines = 2,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Price ($currency)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = !isValid,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Colors.gr,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Colors.gr,
                        unfocusedLabelColor = Color.Gray,
                        errorTextColor = Color.White
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    priceText.replace(",", ".").toDoubleOrNull()?.let(onConfirm)
                },
                enabled = isValid
            ) {
                Text("Save", color = Colors.gr)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        }
    )
}

@Composable
private fun SummaryRow(
    total: Double?,
    itemCount: Int,
    selectedCount: Int,
    currency: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.gr.copy(alpha = 0.15f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                if (total != null) {
                    Text("Total", color = Color.Gray, fontSize = 12.sp)
                    Text(
                        text = "${total.formatAmount()} $currency",
                        color = Colors.gr,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (itemCount > 0) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (selectedCount > 0 && selectedCount < itemCount)
                            "$selectedCount / $itemCount items selected"
                        else
                            "$itemCount items recognized",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ReceiptItemRow(
    item: ReceiptItem,
    currency: String,
    isSelected: Boolean,
    onToggle: () -> Unit,
    onEditPrice: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .background(if (isSelected) Colors.gr.copy(alpha = 0.08f) else Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(
                checkedColor = Colors.gr,
                uncheckedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                color = Color.White,
                maxLines = 2
            )
            val meta = buildList {
                if (item.quantity.isNotBlank()) add("× ${item.quantity}")
                if (item.taxRate.isNotBlank()) add("tax ${item.taxRate}")
            }.joinToString("  ·  ")
            if (meta.isNotEmpty()) {
                Text(text = meta, color = Color.Gray, fontSize = 12.sp)
            }
        }
        Text(
            text = "${item.price.formatAmount()} $currency",
            color = Colors.gr,
            fontWeight = FontWeight.Medium
        )
        IconButton(
            onClick = onEditPrice,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit price",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
