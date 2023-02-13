package com.veles.purchase.presentation.model.sort

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.presentation.R

enum class SortPurchase(val resId: Int) {
    SORTING_A_Z(R.string.sorting_a_z),
    SORTING_Z_A(R.string.sorting_z_a),
    SORTING_DATA_NEW(R.string.sorting_data_new),
    SORTING_DATA_OLD(R.string.sorting_data_old),
    SORTING_CHECK(R.string.sorting_check),
    SORTING_UNCHECK(R.string.sorting_uncheck)
}

fun SortPurchase.toPurchaseComparator(): Comparator<PurchaseModel> {
    return when (this) {
        SortPurchase.SORTING_A_Z -> compareBy(PurchaseModel::check).thenBy(PurchaseModel::text)
        SortPurchase.SORTING_Z_A -> compareBy(PurchaseModel::check).thenByDescending(
            PurchaseModel::text
        )
        SortPurchase.SORTING_DATA_NEW -> compareByDescending(PurchaseModel::createId)
        SortPurchase.SORTING_DATA_OLD -> compareBy(PurchaseModel::createId)
        SortPurchase.SORTING_CHECK -> compareByDescending(PurchaseModel::check).thenByDescending(
            PurchaseModel::createId
        )
        SortPurchase.SORTING_UNCHECK -> compareBy(PurchaseModel::check).thenByDescending(
            PurchaseModel::createId
        )
    }
}
