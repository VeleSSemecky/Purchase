package com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.choose

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseBottomSheetDialogFragment
import com.veles.purchase.presentation.extensions.launchRepeatOnLifecycle
import com.veles.purchase.presentation.model.currency.currencyModelList
import com.veles.purchase.presentation.presentation.compose.Colors

class CurrencyChooseFragment : BaseBottomSheetDialogFragment() {

    private val viewModel: CurrencyChooseViewModel by viewModels { viewModelFactory }

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
                    Content()
                    Save()
                }
            }
        }
    }

    @Composable
    fun Content() {
        val currency by viewModel.flowCurrency.collectAsState()
        AndroidView(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            factory = { context ->
                NumberPicker(context).apply {
                    setOnValueChangedListener { _, _, newVal ->
                        viewModel.setCurrency(
                            currencyModelList[newVal].currency.currencyCode
                        )
                    }
                    minValue = 0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        textSize = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP,
                            10.toFloat(),
                            context.resources.displayMetrics
                        )
                    }
                    maxValue = currencyModelList.size - 1
                    launchRepeatOnLifecycle(Lifecycle.State.RESUMED) {
                        viewModel.flowCurrency.collect { curr ->
                            value =
                                currencyModelList.indexOfFirst {
                                    it.currency.numericCode == curr.numericCode
                                }
                        }
                    }
                    value =
                        currencyModelList.indexOfFirst {
                            it.currency.numericCode == currency.numericCode
                        }

                    displayedValues = currencyModelList.map { it.text }.toTypedArray()
                }
            }
        )
    }

    @Composable
    fun Save() {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(
                        start = 20.dp,
                        end = 10.dp
//                        bottom = 16.dp,
//                        top = 16.dp,
                    ),
                onClick = {
                    viewModel.save()
                    findNavController().popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.colorPrimaryDark
                )
//                contentPadding = PaddingValues(16.dp)
            ) {
                Text(
                    text = "Save",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        start = 10.dp,
                        end = 20.dp
//                        bottom = 16.dp,
//                        top = 16.dp,
                    ),
                onClick = {
                    findNavController().popBackStack()
                    findNavController().navigate(
                        CurrencyChooseFragmentDirections.fragmentCurrencySearch()
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.colorPrimaryDark
                )
//                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "Search"
                )
            }
        }
    }
}
