package com.veles.purchase.presentation.compose.sku.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.SkuSumMonthModel
import com.veles.purchase.presentation.mvvm.sku.statistics.SkuStatisticsViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * SKU Statistics Screen - shows spending statistics
 *
 * Migrated from: OutlayGraphFragment.kt (OutlayGraph)
 *
 * Features:
 * - List of SKUs with total spending
 * - Shows sum per SKU for selected period
 * - Year selection (simplified)
 * - Total sum display
 * - Empty state handling
 *
 * Phase 2.15 - SKU Statistics screen migration
 *
 * Simplified for KMP:
 * - No year/month picker dialogs (can add later)
 * - Shows current year by default
 * - Simple list view
 */

// Colors matching original design
object SkuStatisticsColors {
    val colorPrimary = Color(0xFF212121)    // Toolbar
    val itemBackground = Color(0xFF212121).copy(alpha = 0.8f)
    val gr = Color(0xFF4ACFAC)              // Green accent
    val surface = Color(0xFF000000)         // Black background
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkuStatisticsScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: SkuStatisticsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            SkuStatisticsToolbar(
                title = "Statistics",
                subtitle = uiState.displayPeriod,
                onNavigateBack = onNavigateBack
            )
        },
        containerColor = SkuStatisticsColors.surface
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = SkuStatisticsColors.gr)
            }
        } else {
            SkuStatisticsContent(
                paddingValues = paddingValues,
                uiState = uiState
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
private fun SkuStatisticsToolbar(
    title: String,
    subtitle: String,
    onNavigateBack: () -> Unit
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
            Column {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = SkuStatisticsColors.gr,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SkuStatisticsColors.colorPrimary
        )
    )
}

@Composable
private fun SkuStatisticsContent(
    paddingValues: PaddingValues,
    uiState: com.veles.purchase.presentation.mvvm.sku.statistics.SkuStatisticsUiState
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        // Empty state
        if (uiState.statistics.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No statistics available\nfor ${uiState.displayPeriod}",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
            return
        }

        // Total sum header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SkuStatisticsColors.gr.copy(alpha = 0.2f))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total Spending",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = String.format("%.2f %s",
                    uiState.totalSum,
                    uiState.statistics.firstOrNull()?.currencyCode ?: "UAH"
                ),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = SkuStatisticsColors.gr
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Statistics list
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = uiState.statistics,
                key = { it.skuName }
            ) { item ->
                StatisticsItem(item = item)
            }
        }
    }
}

@Composable
private fun StatisticsItem(item: SkuSumMonthModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkuStatisticsColors.itemBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // SKU name
        Text(
            text = item.skuName,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Sum and currency
        Text(
            text = "${item.sum} ${item.currencyCode}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = SkuStatisticsColors.gr,
            textAlign = TextAlign.End
        )
    }
}