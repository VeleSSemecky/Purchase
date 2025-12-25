package com.veles.purchase.domain.model.fcm

data class NotificationMessageModel(
    val registrationIds: List<String> = arrayListOf(),
    val data: DataModel
)
