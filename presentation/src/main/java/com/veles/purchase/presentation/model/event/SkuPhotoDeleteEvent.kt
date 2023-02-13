package com.veles.purchase.presentation.model.event

import com.veles.purchase.data.room.table.SkuPhotoEntity
import com.veles.purchase.presentation.data.bus.Event
import kotlinx.parcelize.Parcelize

@Parcelize
data class SkuPhotoDeleteEvent(
    val skuPhotoEntity: SkuPhotoEntity
) : Event
