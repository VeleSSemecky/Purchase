package com.veles.purchase.presentation.presentation.mvvm.purchase.setting

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class SettingsPurchase(
    val sizeType: SizeType = SizeType.DP,
    val typeShape: TypeShape = TypeShape.CUT,
    val corners: Corners = Corners.AllCorner(0f),
    val isShowImage: Boolean = true
)

sealed class Corners {
    class AllCorner(val size: Float) : Corners()
    class SideCorner(
        val topStart: Float,
        val topEnd: Float,
        val bottomEnd: Float,
        val bottomStart: Float
    ) : Corners()
}

enum class SizeType {
    DP, PERCENT
}

enum class TypeShape {
    CUT, ROUNDED
}

fun SettingsPurchase.toShape(): Shape {
    val topStart = when (corners) {
        is Corners.AllCorner -> corners.size
        is Corners.SideCorner -> corners.topStart
    }
    val topEnd = when (corners) {
        is Corners.AllCorner -> corners.size
        is Corners.SideCorner -> corners.topEnd
    }
    val bottomStart = when (corners) {
        is Corners.AllCorner -> corners.size
        is Corners.SideCorner -> corners.bottomStart
    }
    val bottomEnd = when (corners) {
        is Corners.AllCorner -> corners.size
        is Corners.SideCorner -> corners.bottomEnd
    }
    return when (typeShape) {
        TypeShape.CUT -> CutCornerShape(
            topStart = sizeType.toCornerSize(topStart),
            topEnd = sizeType.toCornerSize(topEnd),
            bottomEnd = sizeType.toCornerSize(bottomEnd),
            bottomStart = sizeType.toCornerSize(bottomStart)
        )
        TypeShape.ROUNDED -> RoundedCornerShape(
            topStart = sizeType.toCornerSize(topStart),
            topEnd = sizeType.toCornerSize(topEnd),
            bottomEnd = sizeType.toCornerSize(bottomEnd),
            bottomStart = sizeType.toCornerSize(bottomStart)
        )
    }
}

fun SizeType.toCornerSize(value: Float): CornerSize = when (this) {
    SizeType.DP -> CornerSize(size = value.dp)
    SizeType.PERCENT -> CornerSize(percent = value.toInt())
}
