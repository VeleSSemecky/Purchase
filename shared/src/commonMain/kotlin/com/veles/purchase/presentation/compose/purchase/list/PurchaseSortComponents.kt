package com.veles.purchase.presentation.compose.purchase.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.model.sort.SortPurchase
import org.jetbrains.compose.resources.stringResource

@Composable
fun SortIndicator(
    sortPurchase: SortPurchase,
    onSortClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onSortClick,
        color = Colors.surface,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(
                    color = Colors.gr.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .width(IntrinsicSize.Min)
        ) {
            Icon(
                imageVector = Icons.Default.Sort,
                contentDescription = null,
                tint = Colors.gr,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = stringResource(sortPurchase.resId),
                color = Colors.gr,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortSelectionBottomSheet(
    selectedSort: SortPurchase,
    onSortSelected: (SortPurchase) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Colors.colorAccent,
        contentColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Colors.gr.copy(alpha = 0.4f)) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Sort by",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )

            SortItem(
                sort = SortPurchase.SORTING_A_Z,
                icon = Icons.Default.SortByAlpha,
                isSelected = selectedSort == SortPurchase.SORTING_A_Z,
                onClick = { onSortSelected(SortPurchase.SORTING_A_Z) }
            )
            SortItem(
                sort = SortPurchase.SORTING_Z_A,
                icon = Icons.Default.SortByAlpha,
                isSelected = selectedSort == SortPurchase.SORTING_Z_A,
                onClick = { onSortSelected(SortPurchase.SORTING_Z_A) },
                iconRotation = 180f
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                color = Colors.gr.copy(alpha = 0.1f)
            )

            SortItem(
                sort = SortPurchase.SORTING_DATA_NEW,
                icon = Icons.Default.History,
                isSelected = selectedSort == SortPurchase.SORTING_DATA_NEW,
                onClick = { onSortSelected(SortPurchase.SORTING_DATA_NEW) }
            )
            SortItem(
                sort = SortPurchase.SORTING_DATA_OLD,
                icon = Icons.Default.CalendarToday,
                isSelected = selectedSort == SortPurchase.SORTING_DATA_OLD,
                onClick = { onSortSelected(SortPurchase.SORTING_DATA_OLD) }
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                color = Colors.gr.copy(alpha = 0.1f)
            )

            SortItem(
                sort = SortPurchase.SORTING_CHECK,
                icon = Icons.Default.CheckBox,
                isSelected = selectedSort == SortPurchase.SORTING_CHECK,
                onClick = { onSortSelected(SortPurchase.SORTING_CHECK) }
            )
            SortItem(
                sort = SortPurchase.SORTING_UNCHECK,
                icon = Icons.Default.CheckBoxOutlineBlank,
                isSelected = selectedSort == SortPurchase.SORTING_UNCHECK,
                onClick = { onSortSelected(SortPurchase.SORTING_UNCHECK) }
            )
        }
    }
}

@Composable
private fun SortItem(
    sort: SortPurchase,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    iconRotation: Float = 0f
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) Colors.gr else Color.White.copy(alpha = 0.6f),
            modifier = Modifier
                .size(24.dp)
                .let { if (iconRotation != 0f) it.alpha(if (isSelected) 1f else 0.6f) else it }
        )
        Text(
            text = stringResource(sort.resId),
            modifier = Modifier.weight(1f),
            color = if (isSelected) Colors.gr else Color.White,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Colors.gr,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
