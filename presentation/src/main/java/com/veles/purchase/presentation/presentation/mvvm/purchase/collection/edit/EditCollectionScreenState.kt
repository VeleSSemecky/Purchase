package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit

import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.PurchaseCollectionModelUI
import com.veles.purchase.presentation.model.user.UserCheckedUI
import com.veles.purchase.presentation.model.user.UserPurchaseModelUI

data class EditCollectionScreenState(
    val purchaseCollectionModelUI: PurchaseCollectionModelUI,
    val isCollectionNameError: Boolean,
    val listUserChecked: List<UserCheckedUI>,
    val progress: Progress
) {
    companion object {
        val EMPTY = EditCollectionScreenState(
            purchaseCollectionModelUI = PurchaseCollectionModelUI(),
            isCollectionNameError = false,
            listUserChecked = emptyList(),
            progress = Progress.End
        )

        val PREVIEW_STATE = EditCollectionScreenState(
            purchaseCollectionModelUI = PurchaseCollectionModelUI(
                id = "collection1",
                name = "My Collection",
                categoryModels = emptyList()
            ),
            isCollectionNameError = false,
            listUserChecked = listOf(
                UserCheckedUI(
                    userPurchase = UserPurchaseModelUI(
                        uid = "user1",
                        displayName = "Alice Wonderland",
                        email = "alice@example.com"
                    ),
                    isCheck = true
                ),
                UserCheckedUI(
                    userPurchase = UserPurchaseModelUI(
                        uid = "user2",
                        displayName = "Bob The Builder",
                        email = "bob@example.com"
                    ),
                    isCheck = false
                ),
                UserCheckedUI(
                    userPurchase = UserPurchaseModelUI(
                        uid = "user3",
                        displayName = "Charlie Brown",
                        email = "charlie@example.com"
                    ),
                    isCheck = true
                )
            ),
            progress = Progress.End
        )
    }
}

