package com.veles.purchase.presentation.model.currency

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.veles.purchase.presentation.compose.autocomplete.AutoCompleteEntity
import java.util.Currency
import java.util.Locale

data class CurrencyModel(
    val text: String,
    val locale: Locale,
    val currency: Currency
) : AutoCompleteEntity {

    override fun filter(query: String): Boolean {
        return text.lowercase(Locale.getDefault())
            .contains(query.lowercase(Locale.getDefault()))
    }
}

val currencyModelList: List<CurrencyModel> by lazy {
    val list: List<Locale> = Locale.getAvailableLocales().toList()
    val listRes: HashMap<Locale, Currency> = hashMapOf()
    list.map {
        try {
            val currency: Currency? = Currency.getInstance(it)
            if (currency != null && !listRes.containsValue(currency)) {
                listRes[it] = currency
            }
        } catch (exc: Exception) {
            Firebase.crashlytics.recordException(exc)
            // Locale not found
        }
    }
    val e = listRes.toList().sortedBy { it.second.getDisplayName(Locale.getDefault()) }
    e.map {
        CurrencyModel(
            text = it.second.getDisplayName(Locale.getDefault()).plus(" ")
                .plus(it.second.getSymbol(Locale.getDefault())),
            currency = it.second,
            locale = it.first
        )
    }
}

fun filteredCurrencyModelList(value: String) = currencyModelList.filter { entity ->
    entity.filter(value)
}
