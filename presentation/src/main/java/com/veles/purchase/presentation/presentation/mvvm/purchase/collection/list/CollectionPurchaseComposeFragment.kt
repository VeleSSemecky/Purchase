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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
                        isFloatingActionButtonDocked = true,
                        backgroundColor = Color.Black
                    )
                    Progress()
                    DialogDelete()
                }
            }
        }
    }

    @Composable
    fun FAB() {
        FloatingActionButton(
            shape = CircleShape,
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

    @OptIn(ExperimentalMaterialApi::class)
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                list
            ) { _, item ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                    dismissState.isDismissed(DismissDirection.StartToEnd)
                ) {
                    LaunchedEffect(key1 = this@CollectionPurchaseComposeFragment, block = {
                        viewModel.onDeletePurchaseCollections(item)
                        dismissState.snapTo(DismissValue.Default)
                    })
                }
                SwipeToDismiss(state = dismissState, background = {}) {
                    ItemPurchaseCollections(item)
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ItemPurchaseCollections(
        item: PurchaseCollectionModel
    ) {
        Card(
            backgroundColor = Colors.colorAccent,
            shape = RoundedCornerShape(0.dp),
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 92.dp, min = 92.dp)
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
                )

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = item.name,
                    fontSize = 18.sp,
                    style = textStyle1(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 24.dp,
                            bottom = 24.dp
                        )

                )
            }
        }
    }

    @Composable
    fun DialogDelete() {
        val itemState = viewModel.stateFlowDeletePurchaseCollections.collectAsState()
        val item = itemState.value ?: return
        AlertDialog(
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
