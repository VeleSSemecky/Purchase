package com.veles.purchase.data.networking.entity.fcm

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationTokenModelData(val token: String) : Parcelable
