package com.veles.purchase.presentation.model.sort

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.shared.resources.Res
import com.veles.purchase.shared.resources.*
import org.jetbrains.compose.resources.StringResource

enum class SortPurchase(val resId: StringResource) {
    SORTING_A_Z(Res.string.sorting_a_z),
    SORTING_Z_A(Res.string.sorting_z_a),
    SORTING_DATA_NEW(Res.string.sorting_data_new),
    SORTING_DATA_OLD(Res.string.sorting_data_old),
    SORTING_CHECK(Res.string.sorting_check),
    SORTING_UNCHECK(Res.string.sorting_uncheck)
}

fun SortPurchase.toPurchaseComparator(): Comparator<PurchaseModel> {
    return when (this) {
        SortPurchase.SORTING_A_Z -> compareBy(PurchaseModel::text)
            .thenBy(PurchaseModel::isChecked)
            .thenByDescending(PurchaseModel::createId)
        SortPurchase.SORTING_Z_A -> compareByDescending(PurchaseModel::text)
            .thenBy(PurchaseModel::isChecked)
            .thenByDescending(PurchaseModel::createId)
        SortPurchase.SORTING_DATA_NEW -> compareByDescending(PurchaseModel::createId)
        SortPurchase.SORTING_DATA_OLD -> compareBy(PurchaseModel::createId)
        SortPurchase.SORTING_CHECK -> compareByDescending(PurchaseModel::isChecked)
            .thenByDescending(PurchaseModel::createId)
        SortPurchase.SORTING_UNCHECK -> compareBy(PurchaseModel::isChecked)
            .thenByDescending(PurchaseModel::createId)
    }
}
