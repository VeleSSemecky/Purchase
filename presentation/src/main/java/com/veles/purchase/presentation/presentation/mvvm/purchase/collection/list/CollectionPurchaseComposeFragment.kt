package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.extensions.findParentNavController
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.MyTheme
import com.veles.purchase.presentation.presentation.compose.textStyle1
import com.veles.purchase.presentation.presentation.mvvm.purchase.navigation.NavigationFragmentDirections

class CollectionPurchaseComposeFragment : BaseFragment() {

    private val viewModel: CollectionPurchaseComposeViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.compose_view,
            container,
            false
        ).apply {
            findViewById<ComposeView>(R.id.composeView).setContent {
                MyTheme {
                    Scaffold(
                        topBar = {
                        },
                        content = {
                            Content(it)
                        },
                        floatingActionButton = {
                            FAB()
                        },
                        floatingActionButtonPosition = FabPosition.End,
                        containerColor = Colors.surface
                    )
                    Progress()
                    DialogDelete()
                }
            }
        }
    }

    @Composable
    fun FAB() {
        androidx.compose.material3.FloatingActionButton(
            modifier = Modifier,
            onClick = {
                findParentNavController().navigate(
                    NavigationFragmentDirections.fragmentEditCollection()
                )
            }
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "FAB"
            )
        }
    }

    @Composable
    fun Progress() {
        val progress = viewModel.stateFlowProgress.collectAsState()
        if (progress.value != Progress.Start) return
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.progress)
                .clickable(false) {
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Colors.gr
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues()
    ) = Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues = paddingValues)
    ) {
        val list by viewModel.stateFlowListPurchaseCollections.collectAsState()
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalItemSpacing = 20.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                list
            ) { _, item ->
                val dismissState = rememberSwipeToDismissBoxState()
                if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart ||
                    dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd
                ) {
                    LaunchedEffect(key1 = this@CollectionPurchaseComposeFragment, block = {
                        viewModel.onDeletePurchaseCollections(item)
                        dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                    })
                }
                SwipeToDismissBox(state = dismissState, backgroundContent = {}) {
                    ItemPurchaseCollections(item)
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun ItemPurchaseCollections(
        item: PurchaseCollectionModel = PurchaseCollectionModel.TEST
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .background(Color(0xFF38A186))
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {
                                findParentNavController().navigate(
                                    NavigationFragmentDirections.fragmentPurchase(
                                        item.id
                                    )
                                )
                            },
                            onLongClick = {
                                viewModel.onDeletePurchaseCollections(item)
                            }
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = item.name,
                        fontSize = 16.sp,
                        style = textStyle1(),
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
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        painter = painterResource(R.drawable.ic_purchase_collections),
                        contentDescription = "Purchase Collections Icon",
                        tint = Color.White
                    )
                }
            }
        }
    }

    @Composable
    fun DialogDelete() {
        val itemState = viewModel.stateFlowDeletePurchaseCollections.collectAsState()
        val item = itemState.value ?: return
        androidx.compose.material3.AlertDialog(
            title = {
                Text(text = getString(R.string.are_you_sure), color = Color.White, fontSize = 20.sp)
            },
            text = {
                Text(text = getString(R.string.delete_plus_param, item.name), color = Color.White)
            },
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = {
                    viewModel.apiFirebaseRemovePurchaseCollection(item = item)
                    viewModel.onDeletePurchaseCollections(null)
                }) {
                    Text(getString(R.string.yes), color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.onDeletePurchaseCollections(null)
                }) {
                    Text(getString(R.string.no), color = Color.White)
                }
            }
        )
    }
}
