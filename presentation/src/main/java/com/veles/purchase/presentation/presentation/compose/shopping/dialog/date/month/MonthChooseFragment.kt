package com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.month

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseBottomSheetDialogFragment
import com.veles.purchase.presentation.presentation.compose.Colors

class MonthChooseFragment : BaseBottomSheetDialogFragment() {

    private val viewModel: MonthChooseViewModel by viewModels { viewModelFactory }

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
        val localDataMonth by viewModel.flowLocalDataMonth.collectAsState()
        AndroidView(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            factory = { context ->
                NumberPicker(context).apply {
                    setOnValueChangedListener { _, _, newVal -> viewModel.setLocalDataMonth(newVal) }
                    minValue = viewModel.getMonthArrayValue().first()
                    maxValue = viewModel.getMonthArrayValue().last()
                    displayedValues = viewModel.getMonthArrayNames()
                    value = localDataMonth
                }
            }
        )
    }

    @Composable
    fun Save() {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .weight(1f)
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
                    modifier = Modifier.padding(16.dp)
                )
            }
            Button(
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 20.dp,
                        bottom = 16.dp,
                        top = 16.dp
                    ),
                onClick = {
                    viewModel.all()
                    findNavController().popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.colorPrimaryDark
                )
            ) {
                Text(
                    text = "All",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
