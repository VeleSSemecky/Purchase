package com.veles.purchase.presentation.compose.sku.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.ExpenseCategory
import com.veles.purchase.domain.model.SkuItemModel
import com.veles.purchase.presentation.compose.Colors
import coil3.compose.AsyncImage
import com.veles.purchase.presentation.model.UiEvent
import com.veles.purchase.presentation.mvvm.sku.edit.SkuEditParams
import com.veles.purchase.presentation.mvvm.sku.edit.SkuEditViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

val COMMON_CURRENCIES = listOf("UAH", "PLN", "USD", "EUR", "GBP", "CZK", "SEK", "NOK", "CHF")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkuEditScreen(
    skuId: String?,
    prefillName: String? = null,
    prefillPrice: String? = null,
    prefillCategory: String? = null,
    onNavigateBack: () -> Unit = {},
    viewModel: SkuEditViewModel = koinViewModel(
        parameters = { parametersOf(SkuEditParams(skuId, prefillName, prefillPrice, prefillCategory)) }
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentOnNavigateBack by rememberUpdatedState(onNavigateBack)
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.NavigateBack -> currentOnNavigateBack()
                is UiEvent.ShowError -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = run {
                val d = uiState.date
                // Rough epoch millis (UTC) from LocalDateTime
                val daysSinceEpoch = (d.year - 1970) * 365L + d.month.ordinal * 30L + d.day
                daysSinceEpoch * 86_400_000L
            }
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val days = millis / 86_400_000L
                        val year = 1970 + (days / 365).toInt()
                        val dayOfYear = (days % 365).toInt()
                        val month = (dayOfYear / 30).coerceIn(0, 11) + 1
                        val day = ((dayOfYear % 30) + 1).coerceIn(1, 28)
                        val newDate = LocalDateTime(year, month, day, uiState.date.hour, uiState.date.minute)
                        viewModel.onDateChanged(newDate)
                    }
                    showDatePicker = false
                }) { Text("OK", color = Colors.gr) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel", color = Color.Gray) }
            },
            colors = DatePickerDefaults.colors(
                containerColor = Colors.colorPrimary,
                titleContentColor = Color.White,
                headlineContentColor = Colors.gr,
                weekdayContentColor = Color.Gray,
                subheadContentColor = Color.White,
                dayContentColor = Color.White,
                selectedDayContainerColor = Colors.gr,
                selectedDayContentColor = Color.Black,
                todayContentColor = Colors.gr,
                todayDateBorderColor = Colors.gr
            )
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (uiState.showCurrencyPicker) {
        AlertDialog(
            onDismissRequest = viewModel::onDismissCurrencyPicker,
            containerColor = Colors.colorPrimary,
            title = { Text("Select Currency", color = Color.White) },
            text = {
                Column {
                    COMMON_CURRENCIES.forEach { currency ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onCurrencyChanged(currency)
                                    viewModel.onDismissCurrencyPicker()
                                }
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(currency, color = Color.White, fontSize = 16.sp)
                            if (currency == uiState.skuCurrencyCode) {
                                Icon(Icons.Default.Done, null, tint = Colors.gr)
                            }
                        }
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    }
                }
            },
            confirmButton = {}
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                    }
                },
                title = {
                    Text(
                        text = if (!skuId.isNullOrEmpty()) "Edit expense" else "Add expense",
                        fontSize = 20.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.save() }, enabled = !uiState.isSaving) {
                        if (uiState.isSaving) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        } else {
                            Icon(Icons.Default.Done, "Save", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.colorPrimary)
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Colors.surface
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator(color = Colors.gr) }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Receipt photo (shown when expense was created from scanner)
                val receiptImg = uiState.receiptImageBytes
                if (receiptImg != null) {
                    ReceiptPhotoCard(imageBytes = receiptImg)
                }

                // Name
                EditField(
                    value = uiState.skuName,
                    onValueChange = viewModel::onNameChanged,
                    label = "Title",
                    error = uiState.nameError
                )

                // Price + Currency row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    EditField(
                        value = uiState.skuPrice,
                        onValueChange = viewModel::onPriceChanged,
                        label = "Price",
                        error = uiState.priceError,
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedButton(
                        onClick = viewModel::onShowCurrencyPicker,
                        modifier = Modifier.height(56.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Colors.gr),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Colors.gr.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = uiState.skuCurrencyCode,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Colors.gr
                        )
                    }
                }

                // Date picker row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                        .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Date", color = Color.Gray, fontSize = 12.sp)
                        val d = uiState.date
                        val months = listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
                        Text(
                            "${d.day} ${months[d.month.ordinal]} ${d.year}",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                    Icon(Icons.Default.CalendarToday, null, tint = Colors.gr)
                }

                // Category chips
                Text("Category", color = Color.Gray, fontSize = 12.sp)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(ExpenseCategory.entries) { cat ->
                        val isSelected = cat == uiState.category
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.onCategoryChanged(cat) },
                            label = {
                                Text("${cat.emoji} ${cat.displayName}", fontSize = 13.sp)
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Colors.gr,
                                selectedLabelColor = Color.Black,
                                containerColor = Colors.colorPrimary,
                                labelColor = Color.White
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                selectedBorderColor = Colors.gr,
                                borderColor = Color.White.copy(alpha = 0.3f)
                            )
                        )
                    }
                }

                // Comment
                EditField(
                    value = uiState.skuComment,
                    onValueChange = viewModel::onCommentChanged,
                    label = "Comment (optional)",
                    minLines = 2,
                    maxLines = 4
                )

                // Products (line items)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Products (${uiState.items.size})",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Row {
                        if (uiState.items.isNotEmpty()) {
                            TextButton(onClick = viewModel::calculateTotalFromItems) {
                                Icon(Icons.Default.Calculate, null, tint = Colors.gr, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Calculate total", color = Colors.gr, fontSize = 13.sp)
                            }
                        }
                        TextButton(onClick = viewModel::onAddItem) {
                            Icon(Icons.Default.Add, null, tint = Colors.gr, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Add product", color = Colors.gr, fontSize = 13.sp)
                        }
                    }
                }

                uiState.items.forEach { item ->
                    ProductItemEditor(
                        item = item,
                        currency = uiState.skuCurrencyCode,
                        onNameChange = { viewModel.onItemNameChanged(item.skuItemId, it) },
                        onQuantityChange = { viewModel.onItemQuantityChanged(item.skuItemId, it) },
                        onPriceChange = { viewModel.onItemPriceChanged(item.skuItemId, it) },
                        onTaxChange = { viewModel.onItemTaxChanged(item.skuItemId, it) },
                        onRemove = { viewModel.onRemoveItem(item.skuItemId) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun EditField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    minLines: Int = 1,
    maxLines: Int = 1,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        isError = error != null,
        supportingText = error?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        minLines = minLines,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Colors.gr,
            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
            cursorColor = Colors.gr
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
private fun rememberUpdatedState(value: () -> Unit): State<() -> Unit> =
    rememberUpdatedState(newValue = value)

@Composable
private fun ReceiptPhotoCard(imageBytes: ByteArray) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Colors.colorPrimary),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Receipt photo", color = Color.White, fontSize = 14.sp)
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
            if (expanded) {
                AsyncImage(
                    model = imageBytes,
                    contentDescription = "Scanned receipt",
                    contentScale = androidx.compose.ui.layout.ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                )
            }
        }
    }
}

@Composable
private fun ProductItemEditor(
    item: SkuItemModel,
    currency: String,
    onNameChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onTaxChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            EditField(
                value = item.name,
                onValueChange = onNameChange,
                label = "Product",
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onRemove, modifier = Modifier.size(40.dp)) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove product",
                    tint = Color.Red.copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EditField(
                value = item.quantity,
                onValueChange = onQuantityChange,
                label = "Qty",
                modifier = Modifier.weight(1f)
            )
            EditField(
                value = item.price,
                onValueChange = onPriceChange,
                label = "Price ($currency)",
                keyboardType = KeyboardType.Decimal,
                modifier = Modifier.weight(1.2f)
            )
            EditField(
                value = item.taxRate,
                onValueChange = onTaxChange,
                label = "Tax",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

