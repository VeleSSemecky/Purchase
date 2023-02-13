package com.veles.purchase.domain.usecase.price

import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.domain.utill.emptyString
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Inject

class PriceUseCase @Inject constructor(
    private val logger: Logger
) {

    operator fun invoke(): (String) -> String = {
        try {
            val otherSymbols = DecimalFormatSymbols()
            otherSymbols.decimalSeparator = '.'
            otherSymbols.groupingSeparator = '.'
            val df = DecimalFormat("#.##", otherSymbols)
            df.format(it.toBigDecimal())
        } catch (exception: Exception) {
            logger.e(
                PriceUseCase::class.java.name,
                "PriceUseCase ${exception.message}",
                exception
            )
            emptyString()
        }
    }
}
