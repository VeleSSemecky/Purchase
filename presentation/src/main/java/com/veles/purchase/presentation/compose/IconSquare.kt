package com.veles.purchase.presentation.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Composable
fun IconSquare(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    @DrawableRes id: Int,
    contentDescription: String? = null,
    tint: Color = Color.White
) = BoxWithConstraints(modifier = modifier.fillMaxHeight()) {
    Box(
        modifier = Modifier
            .size(maxHeight, maxHeight)
            .align(Alignment.Center)
            .let {
                if (enabled) {
                    it.clickable(
                        enabled = true,
                        onClick = onClick
                    )
                } else {
                    it
                }
            }
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center),
            painter = painterResource(id = id),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun IconSquare(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    imageVector: ImageVector,
    contentDescription: String? = null,
    tint: Color = Color.White
) = BoxWithConstraints(modifier = modifier.fillMaxHeight()) {
    Box(
        modifier = Modifier
            .size(maxHeight, maxHeight)
            .align(Alignment.Center)
            .clickable(
                onClick = onClick
            )
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}
