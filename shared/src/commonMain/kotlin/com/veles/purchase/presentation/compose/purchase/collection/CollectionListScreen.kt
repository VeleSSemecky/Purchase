package com.veles.purchase.presentation.compose.purchase.collection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.presentation.mvvm.purchase.collection.CollectionPurchaseViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Collection List Screen - shows all purchase collections
 *
 * Migrated from: /presentation/src/main/java/com/veles/purchase/presentation/presentation/mvvm/purchase/collection/list/CollectionPurchaseComposeFragment.kt
 *
 * Original design:
 * - 2-column staggered grid
 * - Green collection cards (#38A186)
 * - Swipe to delete
 * - Long press shows delete dialog
 * - FAB to add new collection
 * - Loading indicator
 *
 * Phase 2.5 - Collection feature migration
 */

// Original colors from presentation module
object CollectionColors {
    val colorPrimary = Color(0xff212121)
    val colorPrimaryDark = Color(0xff303030)
    val colorAccent = Color(0xff424242)
    val gr = Color(0xff4ACFAC)  // Green accent
    val surface = Color(0xFF121212)
    val progress = Color(0x99000000)
    val collectionCard = Color(0xFF38A186)  // Green for collection cards
}

@Composable
fun CollectionListScreen(
    viewModel: CollectionPurchaseViewModel = koinViewModel(),
    onNavigateToCollection: (String) -> Unit = {},
    onNavigateToAddCollection: () -> Unit = {}
) {
    Scaffold(
        containerColor = CollectionColors.surface,
        floatingActionButton = {
            FAB(onNavigateToAddCollection)
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
        containerColor = CollectionColors.gr
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add Collection",
            tint = Color.White
        )
    }
}

@Composable
private fun Progress(viewModel: CollectionPurchaseViewModel) {
    val progress by viewModel.stateFlowProgress.collectAsState()
    if (progress != CollectionPurchaseViewModel.ProgressState.Start) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CollectionColors.progress)
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = CollectionColors.gr
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Content(
    viewModel: CollectionPurchaseViewModel,
    onNavigateToCollection: (String) -> Unit
) {
    val list by viewModel.stateFlowListPurchaseCollections.collectAsState()

    if (list.isEmpty()) {
        // Empty state
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
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
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalItemSpacing = 20.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(list) { _, item ->
                val dismissState = rememberSwipeToDismissBoxState()

                if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart ||
                    dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd
                ) {
                    LaunchedEffect(item) {
                        viewModel.onDeletePurchaseCollections(item)
                        dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                    }
                }

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {}
                ) {
                    ItemPurchaseCollection(
                        item = item,
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
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .background(CollectionColors.collectionCard)
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = item.name,
                fontSize = 16.sp,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    )
            )

            // Icon placeholder (will be replaced with actual icon resource later)
            Text(
                text = "📋",
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun DialogDelete(viewModel: CollectionPurchaseViewModel) {
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
        containerColor = CollectionColors.colorPrimaryDark
    )
}