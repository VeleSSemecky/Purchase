package com.veles.purchase.presentation.model.event

import com.veles.purchase.presentation.data.bus.Event
import com.veles.purchase.presentation.model.purchase.PurchasePhotoModelUI
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchasePhotoDeleteEvent(
    val purchasePhotoModel: PurchasePhotoModelUI
) : Event
