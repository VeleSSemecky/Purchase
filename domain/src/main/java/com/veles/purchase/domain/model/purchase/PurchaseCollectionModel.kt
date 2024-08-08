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

        val TEST = PurchaseCollectionModel(
            id = emptyString(),
            name = "Collection Name",
            creator = UserPurchaseModel.EMPTY,
            listMembers = listOf("TEst,TEST")
        )
    }
}

fun PurchaseCollectionModel.isMoreThanOneMembers() = listMembers.size - 1 > 0

fun PurchaseCollectionModel.countAdditionalMembers() = listMembers.size - 1
