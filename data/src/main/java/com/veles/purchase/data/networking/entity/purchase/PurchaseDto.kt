package com.veles.purchase.data.networking.entity.purchase

import android.os.Parcelable
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.utill.emptyString
import java.util.Calendar
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseDto(
    val createId: String = Calendar.getInstance().timeInMillis.toString(),
    val text: String = emptyString(),
    val count: String = emptyString(),
    val isChecked: Boolean = false,
    val price: String = emptyString(),
    val userList: List<String> = emptyList(),
    val listImage: List<PurchasePhotoDto> = emptyList(),
    val purchaseCategoryDto: PurchaseCategoryDto? = null
) : Parcelable

fun PurchaseDto.toPurchaseModel() = PurchaseModel(
    createId = createId,
    text = text,
    count = count,
    check = isChecked,
    price = price,
    userList = userList,
    listImage = listImage.map { it.toPurchasePhotoModel() },
    purchaseCategoryModel = purchaseCategoryDto?.toPurchaseCategoryModel()
)

fun PurchaseModel.toPurchaseModelData() = PurchaseDto(
    createId = createId,
    text = text,
    count = count,
    isChecked = check,
    price = price,
    userList = userList,
    listImage = listImage.map { it.toPurchasePhotoModelData() },
    purchaseCategoryDto = purchaseCategoryModel?.toPurchaseCategoryDto()
)
