package com.veles.purchase.domain.usecase.price

import kotlin.math.roundToInt

class PriceUseCase {

    operator fun invoke(text: String): String {
        if (text.isEmpty()) return "0"
        return try {
            // Simple decimal formatting for KMP
            val value = text.toDouble()
            // Format with 2 decimal places using KMP-compatible approach
            val rounded = (value * 100).roundToInt() / 100.0
            val intPart = rounded.toLong()
            val decPart = ((rounded - intPart) * 100).roundToInt()
            "$intPart.${decPart.toString().padStart(2, '0')}"
        } catch (_: Exception) {
            "0"
        }
    }
}
