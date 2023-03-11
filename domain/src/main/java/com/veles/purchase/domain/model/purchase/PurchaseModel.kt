package com.veles.purchase.domain.model.purchase

import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.domain.utill.zeroString
import java.util.Calendar

data class PurchaseModel(
    val createId: String,
    val text: String,
    val count: String,
    val check: Boolean,
    val price: String,
    val userList: List<String>,
    val listImage: List<PurchasePhotoModel>
) {
    fun getLocalListPurchasePhotoModel(): List<PurchasePhotoModel> =
        listImage.filter { it.status == PhotoStatus.LOCAL }

    fun isEmpty(): Boolean = this == EMPTY

    fun dropImage(purchasePhotoModel: PurchasePhotoModel): List<PurchasePhotoModel> =
        listImage.filterNot { it == purchasePhotoModel }

    companion object {
        val TEST = PurchaseModel(
            createId = "",
            text = "TEST",
            count = "1",
            check = false,
            price = "45",
            userList = emptyList(),
            listImage = emptyList()
        )

        val EMPTY = PurchaseModel(
            createId = Calendar.getInstance().timeInMillis.toString(),
            text = emptyString(),
            count = emptyString(),
            check = false,
            price = zeroString(),
            userList = emptyList(),
            listImage = emptyList()
        )
    }
}
