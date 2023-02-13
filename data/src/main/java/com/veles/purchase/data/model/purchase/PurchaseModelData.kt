package com.veles.purchase.data.model.purchase

import android.os.Parcelable
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.utill.emptyString
import java.util.Calendar
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseModelData(
    val createId: String = Calendar.getInstance().timeInMillis.toString(),
    val text: String = emptyString(),
    val count: String = emptyString(),
    val check: Boolean = false,
    val price: String = emptyString(),
    val userList: List<String> = emptyList(),
    val listImage: List<PurchasePhotoModelData> = emptyList()
) : Parcelable

fun PurchaseModelData?.createIfNull(): PurchaseModelData = this ?: PurchaseModelData()

fun PurchaseModelData.toPurchaseModel() = PurchaseModel(
    createId = createId,
    text = text,
    count = count,
    check = check,
    price = price,
    userList = userList,
    listImage = listImage.map { it.toPurchasePhotoModel() }
)

fun PurchaseModel.toPurchaseModelData() = PurchaseModelData(
    createId = createId,
    text = text,
    count = count,
    check = check,
    price = price,
    userList = userList,
    listImage = listImage.map { it.toPurchasePhotoModelData() }
)
