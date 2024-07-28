package com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.autocomplete.TextSearchBar
import com.veles.purchase.presentation.model.currency.CurrencyModel
import com.veles.purchase.presentation.model.currency.filteredCurrencyModelList
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.textFieldColorsMaterial3
import com.veles.purchase.presentation.presentation.compose.textStyle
import com.veles.purchase.presentation.presentation.compose.textStyle1

class CurrencySearchFragment : BaseFragment() {

    private val viewModel: CurrencySearchViewModel by viewModels { viewModelFactory }

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
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(Color.Black)
                ) {
                    Search()
                    Content(Modifier.weight(1f))
                    Save()
                }
            }
        }
    }

    @Composable
    fun CurrencyItem(currencyModel: CurrencyModel) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = currencyModel.text, fontSize = 18.sp, style = textStyle1())
        }
    }

    @Composable
    fun Search() {
        val value by viewModel.flowValueSearch.collectAsState()
        val view = LocalView.current
        TextSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
            colors = textFieldColorsMaterial3(),
            textStyle = textStyle(),
            value = value,
            label = {
                Text(
                    text = "Search",
                    color = Color.White
                )
            },
            onDoneActionClick = {
                view.clearFocus()
            },
            onClearClick = {
                viewModel.setValueSearch(emptyString())
                viewModel.setCurrencyModelList(filteredCurrencyModelList(emptyString()))
                view.clearFocus()
            },
            onFocusChanged = {
            },
            onValueChanged = { query ->
                viewModel.setValueSearch(query)
                viewModel.setCurrencyModelList(filteredCurrencyModelList(query))
            }
        )
    }

    @Composable
    fun Content(modifier: Modifier = Modifier) {
        val filteredCurrencyModelList by viewModel.flowCurrencyModelList.collectAsState()
        val view = LocalView.current
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(filteredCurrencyModelList) { item ->
                Box(
                    modifier = Modifier.clickable {
                        viewModel.setCurrency(item.currency.currencyCode)
                        viewModel.setValueSearch(item.text)
                        viewModel.setCurrencyModelList(filteredCurrencyModelList(item.text))
                        view.clearFocus()
                    }
                ) {
                    CurrencyItem(item)
                }
            }
        }
    }

    @Composable
    fun Save() {
        Row(
            Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(
                        start = 20.dp,
                        end = 10.dp,
                        bottom = 16.dp,
                        top = 16.dp
                    ),
                onClick = {
                    viewModel.save()
                    findNavController().popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.colorPrimaryDark
                )
            ) {
                Text(
                    text = "Save",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        start = 10.dp,
                        end = 20.dp,
                        bottom = 16.dp,
                        top = 16.dp
                    ),
                onClick = {
                    findNavController().popBackStack()
                    findNavController().navigate(
                        CurrencySearchFragmentDirections.fragmentCurrencyChoose()
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.colorPrimaryDark
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_fortune_wheel),
                    contentDescription = "Search"
                )
            }
        }
    }
}
