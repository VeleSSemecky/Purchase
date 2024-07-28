package com.veles.purchase.presentation.model.purchase

import android.os.Parcelable
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.model.user.UserPurchaseModelUI
import com.veles.purchase.presentation.model.user.toUserPurchaseModel
import com.veles.purchase.presentation.model.user.toUserPurchaseModelUI
import java.util.Calendar
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseCollectionModelUI(
    val id: String = Calendar.getInstance().timeInMillis.toString(),
    val name: String = emptyString(),
    val creator: UserPurchaseModelUI = UserPurchaseModelUI(),
    val listMembers: List<String> = arrayListOf(),
    val count: Int = 0
) : Parcelable

fun PurchaseCollectionModelUI.toPurchaseCollectionModel() = PurchaseCollectionModel(
    id = id,
    name = name,
    creator = creator.toUserPurchaseModel(),
    listMembers = listMembers,
    count = count
)

fun PurchaseCollectionModel.toPurchaseCollectionModelUI() = PurchaseCollectionModelUI(
    id = id,
    name = name,
    creator = creator.toUserPurchaseModelUI(),
    listMembers = listMembers,
    count = count
)
