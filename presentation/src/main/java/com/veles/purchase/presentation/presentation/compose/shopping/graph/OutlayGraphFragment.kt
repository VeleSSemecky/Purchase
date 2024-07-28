package com.veles.purchase.presentation.presentation.compose.shopping.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.veles.purchase.data.room.util.LocalDateTimeConverter.getFullLocalMonthName
import com.veles.purchase.domain.model.SkuSumMonthModel
import com.veles.purchase.domain.utill.zeroInt
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.MyTheme
import java.time.Month
import java.time.format.TextStyle
import java.util.Currency
import java.util.Locale

class OutlayGraphFragment : BaseFragment() {

    private val viewModel: OutlayGraphViewModel by viewModels { viewModelFactory }

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
                            ToolBar()
                        },
                        content = {
                            Content(it)
                        },
                        bottomBar = {
                            BottomBar()
                        },
                        containerColor = Color.Black
                    )
                }
            }
        }
    }

    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues()
    ) {
        val skuSumMonthModelList by viewModel.flowSkuSumMonthModelList.collectAsState()
        LazyColumn(
            modifier = Modifier.padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(skuSumMonthModelList) {
                Item(it)
            }
        }
    }

    @Composable
    fun Item(skuSumMonthModel: SkuSumMonthModel) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Colors.colorPrimary.copy(alpha = 0.8.toFloat()))
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                text = skuSumMonthModel.skuLocalData?.month?.getDisplayName(
                    TextStyle.FULL_STANDALONE,
                    Locale.getDefault()
                ).toString()
                    .replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    },
                color = Colors.gr
            )
            Text(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp),
                text = "${skuSumMonthModel.skuSumMonth} ${
                    Currency.getInstance(skuSumMonthModel.skuCurrencyCode)
                        .getSymbol(Locale.getDefault())
                }",
                color = Colors.gr
            )
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
                    text = "Outlay graph",
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
                            OutlayGraphFragmentDirections.fragmentYearChoose()
                        )
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_done_black_24dp),
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
    fun BottomBar() {
        BottomAppBar(
            contentPadding = PaddingValues(),
            containerColor = Colors.colorPrimaryDark.copy(alpha = 0.8.toFloat())
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (
                    textYear,
                    textMonth
                ) = createRefs()

                val skuYear by viewModel.flowSkuYear.collectAsState()
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .constrainAs(textYear) {
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(textMonth.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                        .fillMaxHeight()
                        .clickable {
                            findNavController().navigate(
                                OutlayGraphFragmentDirections.fragmentYearChoose(
                                    skuYear.toString()
                                )
                            )
                        }
                ) {
                    Text(
                        text = "Year",
                        textAlign = Center,
                        fontSize = 8.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = if (skuYear == zeroInt()) "All" else skuYear.toString(),
                        textAlign = Center,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .constrainAs(textMonth) {
                            end.linkTo(parent.end, margin = 16.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(textMonth.end)
                            width = Dimension.fillToConstraints
                        }
                        .fillMaxHeight()
                        .clickable {
                            findNavController().navigate(
                                OutlayGraphFragmentDirections.fragmentMonthChoose()
                            )
                        }
                ) {
                    val skuMonth by viewModel.flowSkuMonth.collectAsState()
                    Text(
                        text = "Month",
                        textAlign = Center,
                        fontSize = 8.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = if (skuMonth == zeroInt()) {
                            "All"
                        } else {
                            Month.of(skuMonth)
                                .getFullLocalMonthName()
                        },
                        textAlign = Center,
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                createHorizontalChain(
                    textYear,
                    textMonth,
                    chainStyle = ChainStyle.Spread
                )
            }
        }
    }
}
