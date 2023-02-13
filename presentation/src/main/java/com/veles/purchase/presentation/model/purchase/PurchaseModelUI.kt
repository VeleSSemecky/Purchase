package com.veles.purchase.presentation.model.purchase

import android.os.Parcelable
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.utill.emptyString
import java.util.Calendar
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseModelUI(
    val createId: String = Calendar.getInstance().timeInMillis.toString(),
    val text: String = emptyString(),
    val count: String = emptyString(),
    val check: Boolean = false,
    val price: String = emptyString(),
    val userList: List<String> = emptyList(),
    val listImage: List<PurchasePhotoModelUI> = emptyList()
) : Parcelable

fun PurchaseModelUI.toPurchaseModel() = PurchaseModel(
    createId = createId,
    text = text,
    count = count,
    check = check,
    price = price,
    userList = userList,
    listImage = listImage.map { it.toPurchasePhotoModel() }
)

fun PurchaseModel.toPurchaseModelUI() = PurchaseModelUI(
    createId = createId,
    text = text,
    count = count,
    check = check,
    price = price,
    userList = userList,
    listImage = listImage.map { it.toPurchasePhotoModelUI() }
)
