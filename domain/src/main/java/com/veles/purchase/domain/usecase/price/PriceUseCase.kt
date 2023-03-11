package com.veles.purchase.domain.usecase.price

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Inject

class PriceUseCase @Inject constructor() {

    operator fun invoke(text: String): String {
        if (text.isEmpty()) return 0.toString()
        return try {
            val otherSymbols = DecimalFormatSymbols()
            otherSymbols.decimalSeparator = '.'
            otherSymbols.groupingSeparator = '.'
            val df = DecimalFormat("#.##", otherSymbols)
            df.format(text.toBigDecimal())
        } catch (exception: Exception) {
            0.toString()
        }
    }
}
