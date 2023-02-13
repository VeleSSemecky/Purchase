package com.veles.purchase.domain.model.purchase

import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.utill.emptyString

data class PurchaseCollectionModel(
    val id: String,
    val name: String,
    val creator: UserPurchaseModel,
    val listMembers: List<String>
) {

    companion object {

        val EMPTY = PurchaseCollectionModel(
            id = emptyString(),
            name = emptyString(),
            creator = UserPurchaseModel.EMPTY,
            listMembers = emptyList()
        )
    }
}
