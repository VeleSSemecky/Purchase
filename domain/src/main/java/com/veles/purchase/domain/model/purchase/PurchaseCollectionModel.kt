package com.veles.purchase.domain.model.purchase

import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.utill.emptyString

data class PurchaseCollectionModel(
    val id: String,
    val name: String,
    val creator: UserPurchaseModel,
    val listMembers: List<String>,
    val count: Int
) {

    companion object {

        val EMPTY = PurchaseCollectionModel(
            id = emptyString(),
            name = emptyString(),
            creator = UserPurchaseModel.EMPTY,
            listMembers = emptyList(),
            count = 0
        )

        val TEST = PurchaseCollectionModel(
            id = emptyString(),
            name = "Collection Name",
            creator = UserPurchaseModel.EMPTY,
            listMembers = emptyList(),
            count = 0
        )
    }
}
