package com.veles.purchase.data.model.fcm

import android.os.Parcelable
import com.veles.purchase.domain.model.fcm.DataModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModelData(
    val title: String,
    val message: String,
    val tokenSender: String
) : Parcelable

fun DataModelData.toDataModel() = DataModel(
    title = title,
    message = message,
    tokenSender = tokenSender
)

fun DataModel.toDataModelData() = DataModelData(
    title = title,
    message = message,
    tokenSender = tokenSender
)
