package com.veles.purchase.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun ZoomableImage(
    modifier: Modifier = Modifier,
    maxScale: Float = .30f,
    minScale: Float = 3f,
    isRotation: Boolean = false,
    isZoomable: Boolean = true,
    composeImage: @Composable BoxScope.(
        modifier: Modifier
    ) -> Unit
) {
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(1f) }
    val offsetY = remember { mutableStateOf(1f) }

    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .background(Color.Transparent)
            .pointerInput(Unit) {
                if (isZoomable) {
                    awaitEachGesture {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            scale.value *= event.calculateZoom()
                            if (scale.value > 1) {
                                val offset = event.calculatePan()
                                offsetX.value += offset.x
                                offsetY.value += offset.y
                                rotationState.value += event.calculateRotation()
                            } else {
                                scale.value = 1f
                                offsetX.value = 1f
                                offsetY.value = 1f
                            }
                        } while (event.changes.any { it.pressed })
                    }
                }
            }
    ) {
        composeImage(
            modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    if (isZoomable) {
                        scaleX = maxOf(maxScale, minOf(minScale, scale.value))
                        scaleY = maxOf(maxScale, minOf(minScale, scale.value))
                        if (isRotation) {
                            rotationZ = rotationState.value
                        }
                        translationX = offsetX.value
                        translationY = offsetY.value
                    }
                }
        )
    }
}
