package com.veles.purchase.presentation.compose.purchase.collection

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.compose.DismissDirection
import com.veles.purchase.presentation.compose.DismissValue
import com.veles.purchase.presentation.compose.FractionalThreshold
import com.veles.purchase.presentation.compose.SwipeToDismiss
import com.veles.purchase.presentation.compose.rememberDismissState
import com.veles.purchase.presentation.compose.textStyle1
import com.veles.purchase.presentation.mvvm.purchase.collection.CollectionPurchaseComposeViewModel
import com.veles.purchase.shared.resources.Res
import com.veles.purchase.shared.resources.ic_delete_black_24dp
import com.veles.purchase.shared.resources.ic_purchase_collections
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Collection List Screen - shows all purchase collections
 */

// Specific teal color for collection cards (matches original design style)
private val CollectionCardColor = Color(0xFF38A186)

@Composable
fun CollectionListScreen(
    viewModel: CollectionPurchaseComposeViewModel = koinViewModel(),
    onNavigateToCollection: (String) -> Unit = {},
    onNavigateToAddCollection: () -> Unit = {},
) {
    Scaffold(
        containerColor = Colors.surface,
        floatingActionButton = {
            FAB(onNavigateToAddCollection)
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .padding(paddingValues)
        ) {
            Content(
                viewModel = viewModel,
                onNavigateToCollection = onNavigateToCollection
            )
            Progress(viewModel = viewModel)
            DialogDelete(viewModel = viewModel)
        }
    }
}

@Composable
private fun FAB(onNavigateToAddCollection: () -> Unit) {
    FloatingActionButton(
        onClick = onNavigateToAddCollection,
        containerColor = Colors.gr,
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add Collection",
            tint = Color.White
        )
    }
}

@Composable
private fun Progress(viewModel: CollectionPurchaseComposeViewModel) {
    val progress by viewModel.stateFlowProgress.collectAsState()
    if (progress != CollectionPurchaseComposeViewModel.ProgressState.Start) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Colors.gr)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Content(
    viewModel: CollectionPurchaseComposeViewModel,
    onNavigateToCollection: (String) -> Unit
) {
    val list by viewModel.stateFlowListPurchaseCollections.collectAsState()

    if (list.isEmpty()) {
        // Simple Empty state matching original style
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "No collections yet",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Gray
                )
                Text(
                    text = "Tap + to create your first collection",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp), // Proportional padding
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(list) { _, item ->
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                    dismissState.isDismissed(DismissDirection.StartToEnd)
                ) {
                    LaunchedEffect(item) {
                        viewModel.onDeletePurchaseCollections(item)
                        dismissState.snapTo(DismissValue.Default)
                    }
                }

                SwipeToDismiss(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    state = dismissState,
                    background = {
                        val color = when (dismissState.dismissDirection) {
                            DismissDirection.EndToStart -> Color(0xFFE57373)
                            DismissDirection.StartToEnd -> Color(0xFFE57373)
                            else -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                                .background(color),
                            contentAlignment = if (dismissState.dismissDirection == DismissDirection.StartToEnd)
                                Alignment.CenterStart else Alignment.CenterEnd
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_delete_black_24dp),
                                contentDescription = "Delete",
                                tint = Color.White,
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )
                        }
                    },
                    dismissThresholds = { FractionalThreshold(0.7f) }
                ) {
                    val elevation = animateDpAsState(
                        if (dismissState.dismissDirection == null) 4.dp else 0.dp
                    ).value

                    ItemPurchaseCollection(
                        item = item,
                        elevation = elevation,
                        onClick = { onNavigateToCollection(item.id) },
                        onLongClick = { viewModel.onDeletePurchaseCollections(item) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemPurchaseCollection(
    item: PurchaseCollectionModel,
    elevation: androidx.compose.ui.unit.Dp,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(12.dp), // Original simple shape
        colors = CardDefaults.elevatedCardColors(containerColor = CollectionCardColor),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    fontSize = 17.sp,
                    style = textStyle1(), // Using app's original text style
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_purchase_collections),
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(20.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Categories count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.categoryModels.size.toString(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }

                // Members count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Groups,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.listMembers.size.toString(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogDelete(viewModel: CollectionPurchaseComposeViewModel) {
    val item by viewModel.stateFlowDeletePurchaseCollections.collectAsState()
    val collectionToDelete = item ?: return

    AlertDialog(
        title = {
            Text(
                text = "Are you sure?",
                color = Color.White,
                fontSize = 20.sp
            )
        },
        text = {
            Text(
                text = "Delete \"${collectionToDelete.name}\"?",
                color = Color.White
            )
        },
        onDismissRequest = {},
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.apiFirebaseRemovePurchaseCollection(collectionToDelete)
                    viewModel.onDeletePurchaseCollections(null)
                }
            ) {
                Text("Yes", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    viewModel.onDeletePurchaseCollections(null)
                }
            ) {
                Text("No", color = Color.White)
            }
        },
        containerColor = Colors.colorPrimaryDark
    )
}
