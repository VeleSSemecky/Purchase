package com.veles.purchase.presentation.compose.sku.statistics

import com.veles.purchase.domain.utill.formatAmount
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.mvvm.sku.statistics.MonthBarData
import com.veles.purchase.presentation.mvvm.sku.statistics.OutlayGraphViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkuStatisticsScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: OutlayGraphViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                    }
                },
                title = {
                    Text("Statistics", fontSize = 20.sp, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.colorPrimary)
            )
        },
        containerColor = Colors.surface
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Colors.gr)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 16.dp
            )
        ) {
            // Year navigation
            item {
                YearNavigationRow(
                    year = uiState.year,
                    onPrevious = viewModel::onPreviousYear,
                    onNext = viewModel::onNextYear
                )
            }

            // Total sum
            item {
                TotalSumRow(total = uiState.totalSum, currency = uiState.currency)
            }

            // Bar chart
            item {
                BarChart(
                    bars = uiState.monthBars,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Monthly breakdown list
            if (uiState.statistics.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No expenses for ${uiState.year}",
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                }
            } else {
                item {
                    Text(
                        "Monthly breakdown",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
                items(uiState.monthBars.filter { it.sum > 0 }) { bar ->
                    MonthRow(bar = bar, totalSum = uiState.totalSum)
                }
            }
        }

        uiState.error?.let { error ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = { TextButton(onClick = viewModel::clearError) { Text("OK") } }
            ) { Text(error) }
        }
    }
}

@Composable
private fun YearNavigationRow(year: Int, onPrevious: () -> Unit, onNext: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.colorPrimary)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPrevious) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Previous year", tint = Color.White)
        }
        Text(
            text = year.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        IconButton(onClick = onNext) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, "Next year", tint = Color.White)
        }
    }
}

@Composable
private fun TotalSumRow(total: Double, currency: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Total for year", color = Color.Gray, fontSize = 14.sp)
        Text(
            text = "${total.formatAmount()} $currency",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.gr
        )
    }
    HorizontalDivider(color = Color.White.copy(alpha = 0.07f))
}

@Composable
private fun BarChart(bars: List<MonthBarData>, modifier: Modifier = Modifier) {
    val maxValue = bars.maxOfOrNull { it.sum }.takeIf { it != null && it > 0 } ?: 1.0
    val barColor = Colors.gr
    val barBgColor = Color.White.copy(alpha = 0.05f)
    val labelColor = Color.Gray
    val valueColor = Colors.gr.copy(alpha = 0.8f)

    Canvas(modifier = modifier) {
        val totalWidth = size.width
        val totalHeight = size.height
        val labelHeight = 24f
        val chartHeight = totalHeight - labelHeight
        val barWidth = totalWidth / bars.size
        val barPadding = barWidth * 0.2f

        bars.forEachIndexed { index, bar ->
            val x = index * barWidth
            val barH = if (bar.sum > 0) ((bar.sum / maxValue) * chartHeight).toFloat() else 2f
            val barTop = chartHeight - barH

            // Background bar
            drawRoundRect(
                color = barBgColor,
                topLeft = Offset(x + barPadding, 0f),
                size = Size(barWidth - barPadding * 2, chartHeight),
                cornerRadius = CornerRadius(4f, 4f)
            )
            // Value bar
            drawRoundRect(
                color = if (bar.sum > 0) barColor else Color.Transparent,
                topLeft = Offset(x + barPadding, barTop),
                size = Size(barWidth - barPadding * 2, barH),
                cornerRadius = CornerRadius(4f, 4f)
            )
        }
    }

    // Month labels below chart (separate row for text)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        bars.forEach { bar ->
            Text(
                text = bar.label,
                fontSize = 9.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MonthRow(bar: MonthBarData, totalSum: Double) {
    val fraction = if (totalSum > 0) (bar.sum / totalSum).toFloat() else 0f
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(bar.label, color = Color.White, fontSize = 14.sp)
            Text(
                "${bar.sum.formatAmount()} ${bar.currency}",
                color = Colors.gr,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(2.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .fillMaxHeight()
                    .background(Colors.gr, RoundedCornerShape(2.dp))
            )
        }
    }
}
