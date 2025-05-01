package com.veles.purchase.presentation.model.purchase

import android.os.Parcelable
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseCategoryModelUI(
    val id: String,
    val name: String
): Parcelable {
    companion object {
        val INIT_LIST = listOf(
            PurchaseCategoryModelUI(
                id = "0",
                name = "Овочі"
            ),
            PurchaseCategoryModelUI(
                id = "1",
                name = "Фрукти"
            ),
            PurchaseCategoryModelUI(
                id = "2",
                name = "Зелень"
            ),
            PurchaseCategoryModelUI(
                id = "3",
                name = "М'ясо"
            ),
            PurchaseCategoryModelUI(
                id = "4",
                name = "Риба"
            ),
            PurchaseCategoryModelUI(
                id = "5",
                name = "Молочні продукти"
            ),
            PurchaseCategoryModelUI(
                id = "6",
                name = "Хлібобулочні вироби та зернові"
            )
        )

        val EMPTY = PurchaseCategoryModelUI(
            id = "",
            name = ""
        )
    }
}

fun PurchaseCategoryModelUI.toPurchaseCategoryModel() = PurchaseCategoryModel(
    id = id,
    name = name
)

fun PurchaseCategoryModel.toPurchaseCategoryModelUI() = PurchaseCategoryModelUI(
    id = id,
    name = name
)
