package com.veles.purchase.domain.model.setting

data class PurchaseSetting(
    val sizeType: SizeType = SizeType.DP,
    val shapeType: ShapeType = ShapeType.CUT,
    val topStart: Float = 0f,
    val topEnd: Float = 0f,
    val bottomEnd: Float = 0f,
    val bottomStart: Float = 0f,
    val isImage: Boolean = true,
    val isSymmetry: Boolean = true
)
