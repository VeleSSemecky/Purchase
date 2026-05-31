package com.veles.purchase.domain.model

enum class ExpenseCategory(val displayName: String, val emoji: String) {
    FOOD("Food", "🍔"),
    TRANSPORT("Transport", "🚗"),
    HEALTH("Health", "💊"),
    ENTERTAINMENT("Entertainment", "🎬"),
    OTHER("Other", "📦")
}
