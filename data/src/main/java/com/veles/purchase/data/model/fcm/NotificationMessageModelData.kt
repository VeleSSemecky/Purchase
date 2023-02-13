package com.veles.purchase.data.model.fcm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.veles.purchase.domain.model.fcm.NotificationMessageModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationMessageModelData(
    @SerializedName("registration_ids")
    val registrationIds: List<String> = arrayListOf(),
    @SerializedName("data")
    val data: DataModelData
) : Parcelable

fun NotificationMessageModelData.toNotificationMessageModel() = NotificationMessageModel(
    registrationIds = registrationIds,
    data = data.toDataModel()
)

fun NotificationMessageModel.toNotificationMessageModelData() = NotificationMessageModelData(
    registrationIds = registrationIds,
    data = data.toDataModelData()
)
