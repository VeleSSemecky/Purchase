package com.veles.purchase.presentation.presentation.compose.shopping.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.veles.purchase.data.room.core.createPrimaryIDKey
import com.veles.purchase.data.room.table.groupList
import com.veles.purchase.data.room.table.sortedByDescendingLocalDateTime
import com.veles.purchase.data.room.util.LocalDateTimeConverter.getFullLocalMonthName
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.textStyle1
import java.time.Month
import java.util.Currency
import java.util.Locale

class SkuListFragment : BaseFragment() {

    private val viewModel: SkuListViewModel by viewModels { viewModelFactory }

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
                Scaffold(
                    topBar = {
                        ToolBar()
                    },
                    floatingActionButton = {
                        FAB()
                    },
                    bottomBar = {
                        BottomBar()
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    content = {
                        Content(it)
                    },
                    containerColor = Color.Black
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues()
    ) = Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = paddingValues)
    ) {
        val skuEntityList by viewModel.flowSkuEntityList.collectAsState()
        viewModel.initList()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            skuEntityList
                .groupList()
                .forEach { (group, list) ->
                    stickyHeader {
                        CharacterHeader(group)
                    }

                    items(list.sortedByDescendingLocalDateTime()) { item ->
                        ItemExercise(item)
                    }
                }
        }
    }

    @Composable
    fun CharacterHeader(t: Pair<Month, Int>) {
        Text(
            modifier = Modifier
                .background(Colors.colorPrimary.copy(alpha = 0.8.toFloat()))
                .fillMaxWidth()
                .padding(8.dp),
            text = t.first.getFullLocalMonthName()
                .plus(" ").plus(t.second),
            color = Colors.gr
        )
    }

    @Composable
    fun ItemExercise(item: SkuModel) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Colors.colorAccent
            ),
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .clickable {
                    findNavController().navigate(
                        SkuListFragmentDirections.fragmentSkuEdit(
                            item.skuId
                        )
                    )
                }

        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = item.skuName, fontSize = 18.sp, style = textStyle1())
                    Text(
                        text = "${item.skuPrice} ${
                            Currency.getInstance(item.skuCurrencyCode)
                                .getSymbol(Locale.getDefault())
                        }",
                        fontSize = 18.sp,
                        style = textStyle1()
                    )
                    Text(
                        text = "${item.skuLocalData.dayOfMonth}.${item.skuLocalData.month?.ordinal}.${item.skuLocalData.year}",
                        fontSize = 16.sp,
                        style = textStyle1()
                    )
                }
                Box(
                    modifier = Modifier
                        .clickable {
                            viewModel.deleteSkuEntity(item.skuId)
                        }
                        .padding(24.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Colors.gr
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ToolBar() {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = { findNavController().popBackStack() },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = "Localized description"
                    )
                }
            },
            title = {
                Text(
                    text = "Outlay",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        findNavController().navigate(
                            SkuListFragmentDirections.fragmentOutlayGraph()
                        )
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_insert_chart_outlined_24),
                        contentDescription = "Localized description"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Colors.colorPrimary
            )
        )
    }

    @Composable
    fun FAB() {
        FloatingActionButton(
            shape = CircleShape,
            modifier = Modifier,
            onClick = {
                findNavController().navigate(
                    SkuListFragmentDirections.fragmentSkuEdit(
                        createPrimaryIDKey()
                    )
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
    fun BottomBar() {
        BottomAppBar(
            contentPadding = PaddingValues(),
            containerColor = Colors.colorPrimaryDark.copy(alpha = 0.8.toFloat())
        ) {
        }
    }
}
