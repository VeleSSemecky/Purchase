package com.veles.purchase.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.veles.purchase.presentation.presentation.compose.Colors

@Composable
fun CircularCenterProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = Colors.gr
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = color
        )
    }
}
