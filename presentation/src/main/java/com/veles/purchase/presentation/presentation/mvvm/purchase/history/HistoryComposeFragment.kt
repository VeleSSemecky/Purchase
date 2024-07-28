package com.veles.purchase.presentation.presentation.mvvm.purchase.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.IconSquare
import com.veles.purchase.presentation.model.purchase.PurchaseTableModelUI
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.MyTheme
import com.veles.purchase.presentation.presentation.compose.textStyle1
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HistoryComposeFragment : BaseFragment() {

    private val viewModel: HistoryComposeViewModel by viewModels { viewModelFactory }

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
                    ComposeContent()
                }
            }
        }
    }

    @Preview
    @Composable
    fun ComposeContent() {
        Scaffold(
            topBar = {
                ToolBar()
            },
            floatingActionButton = {
            },
            bottomBar = {
            },
            floatingActionButtonPosition = FabPosition.End,
            content = {
                Content(it)
            },
            contentColor = Color.Black
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
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
                    text = requireContext().getString(R.string.history),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = Colors.colorPrimary
            )
        )
    }

    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues()
    ) = Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val purchaseModels by viewModel.stateFlowListHistory.collectAsState(emptyList())
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
        ) {
            items(
                purchaseModels
            ) { item ->
                ItemPurchase(item)
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun ItemPurchase(
        item: PurchaseTableModelUI
    ) {
        Card(
            colors = CardDefaults.cardColors().copy(
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
                .clickable {
//                    findNavController().navigate(
//                        ListPurchaseComposeFragmentDirections.fragmentAddPurchase(
//                            item,
//                            viewModel.getArg().modelCollectionPurchase
//                        )
//                    )
                }

        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                            painter = painterResource(
                                if (item.check) R.drawable.image else R.drawable.no_image
                            ),
                            contentDescription = "Is Image",
                            tint = Colors.gr
                        )
                    }
                    Text(
                        text = item.text,
                        fontSize = 18.sp,
                        style = textStyle1(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                    Checkbox(
                        checked = item.check,
                        onCheckedChange = {
//                            viewModel.onChecked(item, index)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Colors.gr,
                            uncheckedColor = Colors.gr,
                            checkmarkColor = Color.Black
                        )
                    )
                }
                FlowRow(modifier = Modifier.fillMaxWidth()) {
                    ElevatedAssistChip(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = {},
                        label = {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .align(alignment = Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = requireContext().getString(item.typeHistory.text),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }
                        },
                        leadingIcon = {
                            IconSquare(
                                enabled = false,
                                id = R.drawable.ic_press,
                                tint = Colors.gr
                            )
                        },
                        shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 50))
                    )
                    ElevatedAssistChip(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = {},
                        label = {
                            Text(
                                text = let {
                                    val ins = Instant.ofEpochMilli(item.time)
                                        .atZone(ZoneId.systemDefault())
                                    val form = DateTimeFormatter.ofPattern("HH:mm")
                                        .withZone(ZoneId.systemDefault())
                                    form.format(ins)
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                            )
                        },
                        leadingIcon = {
                            IconSquare(
                                enabled = false,
                                id = R.drawable.ic_baseline_access_time_24,
                                tint = Colors.gr
                            )
                        },
                        shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 50))
                    )
                    ElevatedAssistChip(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = {},
                        label = {
                            Text(
                                text = let {
                                    val ins = Instant.ofEpochMilli(item.time)
                                        .atZone(ZoneId.systemDefault())
                                    val form = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                                        .withZone(ZoneId.systemDefault())
                                    form.format(ins)
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        },
                        leadingIcon = {
                            IconSquare(
                                enabled = false,
                                id = R.drawable.ic_baseline_access_time_24,
                                tint = Colors.gr
                            )
                        },
                        shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 50))
                    )
                }
            }
        }
    }
}
