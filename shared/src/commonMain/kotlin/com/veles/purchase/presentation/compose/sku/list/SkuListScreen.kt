package com.veles.purchase.presentation.compose.sku.list

import com.veles.purchase.domain.utill.formatAmount
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.ExpenseCategory
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.model.UiEvent
import com.veles.purchase.presentation.mvvm.sku.list.MonthGroup
import com.veles.purchase.presentation.mvvm.sku.list.SkuListViewModel
import com.veles.purchase.shared.resources.Res
import com.veles.purchase.shared.resources.ic_baseline_insert_chart_outlined_24
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkuListScreen(
    onNavigateToSkuEdit: (String?) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateToStatistics: () -> Unit = {},
    onNavigateToReceiptScanner: () -> Unit = {},
    onNavigateToAiSetup: () -> Unit = {},
    viewModel: SkuListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var fabExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.ShowError -> snackbarHostState.showSnackbar(event.message)
                is UiEvent.NavigateBack -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
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
                            text = "Expenses",
                            fontSize = 20.sp,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = {
                        IconButton(onClick = onNavigateToStatistics) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_baseline_insert_chart_outlined_24),
                                contentDescription = "Statistics",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = onNavigateToAiSetup) {
                            Icon(
                                imageVector = Icons.Default.SmartToy,
                                contentDescription = "AI Setup",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.colorPrimary)
                )
                // Search field
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::onSearchQueryChanged,
                    placeholder = { Text("Search expenses...", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.Gray) },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                                Icon(Icons.Default.Clear, null, tint = Color.Gray)
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.colorPrimary)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Colors.gr,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        focusedContainerColor = Colors.surface,
                        unfocusedContainerColor = Colors.surface
                    )
                )
            }
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                AnimatedVisibility(visible = fabExpanded) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        FabOption(
                            label = "Scan receipt",
                            icon = Icons.Default.QrCodeScanner,
                            onClick = {
                                fabExpanded = false
                                onNavigateToReceiptScanner()
                            }
                        )
                        FabOption(
                            label = "Add manually",
                            icon = Icons.Default.Edit,
                            onClick = {
                                fabExpanded = false
                                onNavigateToSkuEdit(null)
                            }
                        )
                    }
                }
                FloatingActionButton(
                    onClick = { fabExpanded = !fabExpanded },
                    containerColor = Colors.gr,
                    contentColor = Color.Black
                ) {
                    Icon(
                        imageVector = if (fabExpanded) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = if (fabExpanded) "Close" else "Add expense"
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Colors.surface
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Colors.gr)
            }
            return@Scaffold
        }

        if (uiState.filteredSkus.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("💸", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (uiState.searchQuery.isNotEmpty()) "No results for \"${uiState.searchQuery}\"" else "No expenses yet\nTap + to add your first",
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
            }
            return@Scaffold
        }

        ExpensesGroupedList(
            groups = uiState.groupedByMonth,
            paddingValues = paddingValues,
            onSkuClick = { sku -> onNavigateToSkuEdit(sku.skuId) },
            onDeleteSku = viewModel::onDeleteSku,
            onFabCollapse = { if (fabExpanded) fabExpanded = false }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ExpensesGroupedList(
    groups: List<MonthGroup>,
    paddingValues: PaddingValues,
    onSkuClick: (SkuModel) -> Unit,
    onDeleteSku: (String) -> Unit,
    onFabCollapse: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding() + 4.dp,
            bottom = paddingValues.calculateBottomPadding() + 80.dp
        ),
        modifier = Modifier.fillMaxSize().clickable(onClick = onFabCollapse, indication = null, interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() })
    ) {
        groups.forEach { group ->
            stickyHeader(key = group.label) {
                MonthHeader(group = group)
            }
            items(items = group.items, key = { it.skuId }) { sku ->
                ExpenseItem(
                    sku = sku,
                    onClick = { onSkuClick(sku) },
                    onDelete = { onDeleteSku(sku.skuId) }
                )
            }
        }
    }
}

@Composable
private fun MonthHeader(group: MonthGroup) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = group.label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray.copy(alpha = 0.8f)
        )
        Text(
            text = "${group.monthSum.formatAmount()} ${group.currency}",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Colors.gr
        )
    }
    HorizontalDivider(color = Color.White.copy(alpha = 0.06f))
}

@Composable
private fun ExpenseItem(
    sku: SkuModel,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val monthNames = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val d = sku.skuLocalData
    val dateStr = "${d.day} ${monthNames[d.month.ordinal]}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category icon circle
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Colors.gr.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = sku.category.emoji, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Name + date
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = sku.skuName,
                fontSize = 15.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$dateStr · ${sku.category.displayName}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // Amount
        Text(
            text = "${(sku.skuPrice.toDoubleOrNull() ?: 0.0).formatAmount()} ${sku.skuCurrencyCode}",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Colors.gr
        )

        Spacer(modifier = Modifier.width(4.dp))

        // Delete
        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Red.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(start = 68.dp))
}

@Composable
private fun FabOption(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = Colors.colorPrimary.copy(alpha = 0.95f),
            shadowElevation = 2.dp
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        FloatingActionButton(
            onClick = onClick,
            containerColor = Colors.colorPrimary,
            contentColor = Color.White,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(icon, contentDescription = label, modifier = Modifier.size(20.dp))
        }
    }
}

