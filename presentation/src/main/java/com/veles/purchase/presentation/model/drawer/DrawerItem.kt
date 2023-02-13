package com.veles.purchase.presentation.model.drawer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.veles.purchase.presentation.R

enum class DrawerItem(
    @StringRes val resText: Int,
    @DrawableRes val resImage: Int
) {
    HISTORY(R.string.history, R.drawable.ic_baseline_access_time_24),
    SKU_LIST(R.string.history_pays, R.drawable.ic_baseline_payment_24),
    PIP(R.string.pip, R.drawable.ic_baseline_camera_alt_24),
    SETTING(R.string.setting, R.drawable.ic_baseline_settings_24)
}
