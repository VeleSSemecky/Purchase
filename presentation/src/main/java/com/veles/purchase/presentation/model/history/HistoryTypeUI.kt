package com.veles.purchase.presentation.model.history

import androidx.annotation.StringRes
import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.presentation.R

enum class HistoryTypeUI(val type: Int, @StringRes val text: Int) {
    CHECK(1, R.string.checked),
    ADD(2, R.string.added),
    CHANGE(3, R.string.changed),
    DELETE(4, R.string.deleted),
    UNCHECK(5, R.string.unchecked)
}

fun HistoryType.toHistoryTypeUI() = HistoryTypeUI.valueOf(this.name)
