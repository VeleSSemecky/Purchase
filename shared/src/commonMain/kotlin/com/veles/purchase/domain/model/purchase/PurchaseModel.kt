@file:OptIn(ExperimentalTime::class)

package com.veles.purchase.domain.model.purchase

import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.domain.utill.zeroString
import kotlin.time.ExperimentalTime

data class PurchaseModel(
    val createId: String,
    val text: String,
    val count: String,
    val isChecked: Boolean,
    val price: String,
    val userList: List<String>,
    val listImage: List<PurchasePhotoModel>,
    val purchaseCategoryModel: PurchaseCategoryModel?
) {
    fun getLocalListPurchasePhotoModel(): List<PurchasePhotoModel> =
        listImage.filter { it.status == PhotoStatus.LOCAL }

    fun isEmpty(): Boolean = this == EMPTY

    fun dropImage(purchasePhotoModel: PurchasePhotoModel): List<PurchasePhotoModel> =
        listImage.filterNot { it == purchasePhotoModel }

    companion object {
        val TEST = PurchaseModel(
            createId = "",
            text = "TEST dsfsf asdf aasdf  aefasdf a af",
            count = "1",
            isChecked = false,
            price = "45",
            userList = emptyList(),
            listImage = emptyList(),
            purchaseCategoryModel = PurchaseCategoryModel.TEST
        )

        val EMPTY = PurchaseModel(
            createId = kotlin.time.Clock.System.now().toEpochMilliseconds().toString(),
            text = emptyString(),
            count = emptyString(),
            isChecked = false,
            price = zeroString(),
            userList = emptyList(),
            listImage = emptyList(),
            purchaseCategoryModel = null
        )
    }
}
