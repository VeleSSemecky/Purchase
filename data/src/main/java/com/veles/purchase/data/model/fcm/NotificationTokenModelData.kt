package com.veles.purchase.data.model.fcm

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationTokenModelData(val token: String) : Parcelable
