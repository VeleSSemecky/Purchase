package com.veles.purchase.presentation.compose.purchase.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.compose.textStyle1
import com.veles.purchase.presentation.compose.textStyle2
import com.veles.purchase.shared.resources.*
import com.veles.purchase.shared.resources.Res
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PurchaseItem(
    purchase: PurchaseModel,
    elevation: Dp,
    isImageSettingEnabled: Boolean,
    onChecked: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isChecked = purchase.isChecked

    // Animations for checked state
    val contentAlpha by animateFloatAsState(if (isChecked) 0.5f else 1f)
    val textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
    val scale by animateFloatAsState(if (isChecked) 0.98f else 1f)
    val containerColor by animateColorAsState(
        if (isChecked) Colors.colorAccent.copy(alpha = 0.6f) else Colors.colorAccent
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .combinedClickable(
                onClick = onItemClick,
                onLongClick = { /* TODO: Context menu */ }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .graphicsLayer {
                    alpha = contentAlpha
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image indicator
            if (isImageSettingEnabled) {
                PurchaseImageIndicator(hasImages = purchase.listImage.isNotEmpty())
                Spacer(modifier = Modifier.width(12.dp))
            }

            // Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = purchase.text,
                    fontSize = 18.sp,
                    style = textStyle1().copy(
                        textDecoration = textDecoration,
                        fontWeight = if (isChecked) FontWeight.Normal else FontWeight.Medium
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (purchase.count.isNotEmpty()) {
                    Text(
                        text = purchase.count,
                        fontSize = 14.sp,
                        style = textStyle2(),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                if (purchase.purchaseCategoryModel != null || purchase.listImage.isNotEmpty()) {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CategoryChip(categoryName = purchase.purchaseCategoryModel?.name)
                        if (purchase.listImage.isNotEmpty() && !isImageSettingEnabled) {
                            PhotoIndicator(count = purchase.listImage.size)
                        }
                    }
                }
            }

            // Checkbox
            Box(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clickable { onChecked() },
                contentAlignment = Alignment.Center
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Colors.gr,
                        uncheckedColor = Colors.gr.copy(alpha = 0.6f),
                        checkmarkColor = Color.Black
                    )
                )
            }
        }
    }
}

@Composable
private fun PurchaseImageIndicator(hasImages: Boolean) {
    Surface(
        shape = CircleShape,
        color = Colors.gr.copy(alpha = 0.1f),
        modifier = Modifier.size(40.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(if (hasImages) Res.drawable.image else Res.drawable.no_image),
                contentDescription = null,
                tint = if (hasImages) Colors.gr else Color.White.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun PhotoIndicator(count: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .background(Colors.gr.copy(alpha = 0.1f), CircleShape)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PhotoLibrary,
            contentDescription = null,
            tint = Colors.gr,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = count.toString(),
            fontSize = 11.sp,
            color = Colors.gr,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CategoryChip(categoryName: String?) {
    if (categoryName == null) return
    Box(
        modifier = Modifier
            .padding(top = 6.dp)
            .background(
                color = Colors.gr.copy(alpha = 0.1f),
                shape = CircleShape
            )
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = categoryName,
            fontSize = 12.sp,
            style = TextStyle(
                color = Colors.gr,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}
