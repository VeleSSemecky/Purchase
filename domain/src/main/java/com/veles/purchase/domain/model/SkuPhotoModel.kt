package com.veles.purchase.domain.model

import com.veles.purchase.domain.utill.emptyString

data class SkuPhotoModel(
    val skuPhotoId: String,
    val skuPhotoUri: String = emptyString(),
    val skuId: String
)
