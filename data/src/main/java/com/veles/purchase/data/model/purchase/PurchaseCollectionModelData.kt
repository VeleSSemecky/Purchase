package com.veles.purchase.data.model.purchase

import android.os.Parcelable
import com.veles.purchase.data.model.user.UserPurchaseModelData
import com.veles.purchase.data.model.user.toUserPurchaseModel
import com.veles.purchase.data.model.user.toUserPurchaseModelData
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.utill.emptyString
import java.util.Calendar
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseCollectionModelData(
    val id: String = Calendar.getInstance().timeInMillis.toString(),
    val name: String = emptyString(),
    val creator: UserPurchaseModelData = UserPurchaseModelData(),
    val listMembers: List<String> = arrayListOf()
) : Parcelable

fun PurchaseCollectionModelData.toPurchaseCollectionModel() = PurchaseCollectionModel(
    id = id,
    name = name,
    creator = creator.toUserPurchaseModel(),
    listMembers = listMembers
)

fun PurchaseCollectionModel.toPurchaseCollectionModelData() = PurchaseCollectionModelData(
    id = id,
    name = name,
    creator = creator.toUserPurchaseModelData(),
    listMembers = listMembers
)
