package com.veles.purchase.presentation.compose.purchase.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.history.PurchaseHistoryModel
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.compose.textStyle1
import com.veles.purchase.presentation.mvvm.purchase.history.HistoryViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * History Screen - shows purchase history timeline
 *
 * Migrated from: HistoryComposeFragment.kt
 *
 * Displays:
 * - Historical events (add, check, modify, delete, uncheck)
 * - Event timestamp (time and date)
 * - Purchase details for each event
 * - Type chips showing event type
 *
 * Phase 2.10 - History screen migration
 * FIXED: Now using custom components matching pattern exactly
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    collectionId: String,
    onNavigateBack: () -> Unit = {},
    viewModel: HistoryViewModel = koinViewModel(
        parameters = { parametersOf(collectionId) }
    )
) {
    val historyList by viewModel.historyList.collectAsState()

    Scaffold(
        topBar = {
            HistoryToolbar(onNavigateBack = onNavigateBack)
        },
        containerColor = Colors.surface
    ) { paddingValues ->
        HistoryContent(
            paddingValues = paddingValues,
            historyList = historyList
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryToolbar(onNavigateBack: () -> Unit) {
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
                text = "History",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.colorPrimary
        )
    )
}

@Composable
private fun HistoryContent(
    paddingValues: PaddingValues,
    historyList: List<PurchaseHistoryModel>
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        if (historyList.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No history yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(historyList) { item ->
                    HistoryItem(item)
                }
            }
        }
    }
}

@Composable
private fun HistoryItem(item: PurchaseHistoryModel) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Colors.colorAccent
        ),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top row: icon, text, checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image indicator text
                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = if (item.hasImages) "📷" else "  ",
                        fontSize = 24.sp,
                        color = Colors.gr
                    )
                }

                // Purchase name
                Text(
                    text = item.purchaseName,
                    fontSize = 18.sp,
                    style = textStyle1(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )

                // Checkbox
                Checkbox(
                    checked = item.isChecked,
                    onCheckedChange = null, // Read-only
                    colors = CheckboxDefaults.colors(
                        checkedColor = Colors.gr,
                        uncheckedColor = Colors.gr,
                        checkmarkColor = Color.Black,
                        disabledCheckedColor = Colors.gr,
                        disabledUncheckedColor = Colors.gr
                    ),
                    enabled = false
                )
            }

            // Bottom row: chips for type and timestamp
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                // History type chip
                HistoryTypeChip(item.historyType)

                // Time chip
                TimeChip(item.timestamp)

                // Date chip
                DateChip(item.timestamp)
            }
        }
    }
}

@Composable
private fun HistoryTypeChip(historyType: HistoryType) {
    val text = when (historyType) {
        HistoryType.CHECK -> "✓ Checked"
        HistoryType.ADD -> "+ Added"
        HistoryType.CHANGE -> "~ Modified"
        HistoryType.DELETE -> "✗ Deleted"
        HistoryType.UNCHECK -> "○ Unchecked"
    }

    ElevatedAssistChip(
        modifier = Modifier.padding(start = 8.dp),
        onClick = {},
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        },
        shape = CircleShape,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = Colors.colorAccent,
            labelColor = Color.White
        )
    )
}

@OptIn(kotlin.time.ExperimentalTime::class)
@Composable
private fun TimeChip(timestamp: Long) {
    val instant = kotlinx.datetime.Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val timeText = "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"

    ElevatedAssistChip(
        modifier = Modifier.padding(start = 8.dp),
        onClick = {},
        label = {
            Text(
                text = "🕐 $timeText",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        },
        shape = CircleShape,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = Colors.colorAccent,
            labelColor = Color.White
        )
    )
}

@OptIn(kotlin.time.ExperimentalTime::class)
@Suppress("DEPRECATION")
@Composable
private fun DateChip(timestamp: Long) {
    val instant = kotlinx.datetime.Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val monthNames = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val dateText = "${localDateTime.dayOfMonth} ${monthNames[localDateTime.monthNumber - 1]} ${localDateTime.year}"

    ElevatedAssistChip(
        modifier = Modifier.padding(start = 8.dp),
        onClick = {},
        label = {
            Text(
                text = "📅 $dateText",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        },
        shape = CircleShape,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = Colors.colorAccent,
            labelColor = Color.White
        )
    )
}
