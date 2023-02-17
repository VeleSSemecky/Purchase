package com.veles.purchase.presentation.model.setting

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.model.setting.ShapeType
import com.veles.purchase.domain.model.setting.SizeType

data class CornerSetting(
    val topStart: Float = 0f,
    val topEnd: Float = 0f,
    val bottomStart: Float = 0f,
    val bottomEnd: Float = 0f
) {
    val size: Float =
        if (topStart - topEnd + bottomStart - bottomEnd == 0.toFloat()) topStart else 0.toFloat()

    constructor(size: Float = 0f) : this(size, size, size, size)
}

fun PurchaseSetting.setCorner(
    allCorner: CornerSetting,
    sideCorner: CornerSetting
): PurchaseSetting {
    val (topStart, topEnd, bottomStart, bottomEnd) = when (isSymmetry) {
        true -> allCorner
        false -> sideCorner
    }

    return copy(
        topStart = topStart,
        topEnd = topEnd,
        bottomStart = bottomStart,
        bottomEnd = bottomEnd
    )
}

fun PurchaseSetting.toCornerSetting() = CornerSetting(
    topStart,
    topEnd,
    bottomStart,
    bottomEnd
)

fun PurchaseSetting.toShape(): Shape = when (shapeType) {
    ShapeType.CUT -> CutCornerShape(
        topStart = sizeType.toCornerSize(topStart),
        topEnd = sizeType.toCornerSize(topEnd),
        bottomEnd = sizeType.toCornerSize(bottomEnd),
        bottomStart = sizeType.toCornerSize(bottomStart)
    )
    ShapeType.ROUNDED -> RoundedCornerShape(
        topStart = sizeType.toCornerSize(topStart),
        topEnd = sizeType.toCornerSize(topEnd),
        bottomEnd = sizeType.toCornerSize(bottomEnd),
        bottomStart = sizeType.toCornerSize(bottomStart)
    )
}

fun SizeType.toCornerSize(value: Float): CornerSize = when (this) {
    SizeType.DP -> CornerSize(size = value.dp)
    SizeType.PERCENT -> CornerSize(percent = value.toInt())
}
