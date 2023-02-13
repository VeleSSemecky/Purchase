package com.veles.purchase.domain.model.purchase

data class PurchaseModel(
    val createId: String,
    val text: String,
    val count: String,
    val check: Boolean,
    val price: String,
    val userList: List<String>,
    val listImage: List<PurchasePhotoModel>
) {

    fun getLocalListPurchasePhotoModel() = listImage.filter { it.status == PhotoStatus.LOCAL }

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
    }
}
