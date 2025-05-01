package com.veles.purchase.data.networking.entity.purchase

import android.os.Parcelable
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseCategoryDto(
    val id: String = "",
    val name: String = ""
) : Parcelable

fun PurchaseCategoryDto.toPurchaseCategoryModel() = PurchaseCategoryModel(
    id = id,
    name = name
)

fun PurchaseCategoryModel.toPurchaseCategoryDto() = PurchaseCategoryDto(
    id = id,
    name = name
)
