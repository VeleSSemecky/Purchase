package com.veles.purchase.presentation.presentation.mvvm.purchase.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseBottomSheetDialogFragment
import com.veles.purchase.presentation.model.sort.SortPurchase
import com.veles.purchase.presentation.presentation.compose.Colors

class SortPurchaseFragment : BaseBottomSheetDialogFragment() {

    private val viewModel: SortPurchaseViewModel by viewModelsWithFactory()

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
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        Column(
            modifier = Modifier.background(
                color = Colors.colorAccent,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
        ) {
            SortPurchase.values().forEach { sortPurchase ->
                Item(sortPurchase)
            }
        }
    }

    @Composable
    private fun Item(sortPurchase: SortPurchase) {
        Text(
            text = getString(sortPurchase.resId),
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { viewModel.sendSort(sortPurchase) })
                .padding(16.dp),
            color = Colors.gr
        )
    }
}
