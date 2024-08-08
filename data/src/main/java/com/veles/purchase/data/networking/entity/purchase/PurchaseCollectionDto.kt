package com.veles.purchase.data.networking.entity.purchase

import android.os.Parcelable
import com.veles.purchase.data.networking.entity.user.UserDto
import com.veles.purchase.data.networking.entity.user.toUserPurchaseModel
import com.veles.purchase.data.networking.entity.user.toUserDto
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.utill.emptyString
import java.util.Calendar
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseCollectionDto(
    val id: String = Calendar.getInstance().timeInMillis.toString(),
    val name: String = emptyString(),
    val creator: UserDto = UserDto(),
    val listMembers: List<String> = arrayListOf()
) : Parcelable

fun PurchaseCollectionDto.toPurchaseCollectionModel() = PurchaseCollectionModel(
    id = id,
    name = name,
    creator = creator.toUserPurchaseModel(),
    listMembers = listMembers
)

fun PurchaseCollectionModel.toPurchaseCollectionModelData() = PurchaseCollectionDto(
    id = id,
    name = name,
    creator = creator.toUserDto(),
    listMembers = listMembers
)
