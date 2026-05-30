package com.veles.purchase.presentation.compose.sku.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.compose.textStyle1
import com.veles.purchase.presentation.model.UiEvent
import com.veles.purchase.presentation.mvvm.sku.list.SkuListViewModel
import com.veles.purchase.shared.resources.Res
import com.veles.purchase.shared.resources.ic_baseline_insert_chart_outlined_24
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * SKU List Screen - shows all shopping items
 *
 * Migrated from: SkuListFragment.kt
 *
 * Features:
 * - List of SKUs with name, comment, and price
 * - Delete functionality (delete button, no swipe)
 * - Search functionality
 * - FAB to add new SKU
 * - Empty state handling
 *
 * Phase 2.13 - SKU List screen migration
 * FIXED: Now using custom components matching pattern exactly
 *
 * SKU = Stock Keeping Unit - items you buy frequently
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkuListScreen(
    onNavigateToSkuEdit: (String?) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateToStatistics: () -> Unit = {},
    viewModel: SkuListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

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
            SkuListToolbar(
                onNavigateBack = onNavigateBack,
                onNavigateToStatistics = onNavigateToStatistics
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToSkuEdit(null) },
                containerColor = Colors.gr,
                contentColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add SKU"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Colors.surface
    ) { paddingValues ->
        SkuListContent(
            paddingValues = paddingValues,
            uiState = uiState,
            onSkuClick = { sku ->
                onNavigateToSkuEdit(sku.skuId)
            },
            onDeleteSku = viewModel::onDeleteSku
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SkuListToolbar(
    onNavigateBack: () -> Unit,
    onNavigateToStatistics: () -> Unit
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
                text = "Shopping",
                textAlign = TextAlign.Center,
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
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.colorPrimary
        )
    )
}

@Composable
private fun SkuListContent(
    paddingValues: PaddingValues,
    uiState: com.veles.purchase.presentation.mvvm.sku.list.SkuListUiState,
    onSkuClick: (SkuModel) -> Unit,
    onDeleteSku: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        // Loading indicator
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Colors.gr)
            }
            return
        }

        // Empty state
        if (uiState.filteredSkus.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No shopping items yet\nTap + to add your first item",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
            return
        }

        // SKU list
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = uiState.filteredSkus,
                key = { it.skuId }
            ) { sku ->
                SkuItem(
                    sku = sku,
                    onClick = { onSkuClick(sku) },
                    onDelete = { onDeleteSku(sku.skuId) }
                )
            }
        }
    }
}

@Composable
private fun SkuItem(
    sku: SkuModel,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Colors.colorAccent
        ),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side: Name and comment
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = sku.skuName,
                    fontSize = 18.sp,
                    style = textStyle1(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (sku.skuComment.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = sku.skuComment,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${sku.skuPrice} ${sku.skuCurrencyCode}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Colors.gr
                )
            }

            // Right side: Delete button
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Red.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}
