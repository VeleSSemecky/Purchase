//[data](../../../index.md)/[com.veles.purchase.data.room.table](../index.md)/[SkuSumMonthRelations](index.md)

# SkuSumMonthRelations

[androidJvm]\
data class [SkuSumMonthRelations](index.md)(val skuSumMonth: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val skuMonth: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val skuLocalData: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)?, val skuCurrencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Currency.getInstance(Locale.getDefault()).currencyCode)

## Constructors

| | |
|---|---|
| [SkuSumMonthRelations](-sku-sum-month-relations.md) | [androidJvm]<br>constructor(skuSumMonth: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, skuMonth: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, skuLocalData: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)?, skuCurrencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Currency.getInstance(Locale.getDefault()).currencyCode) |

## Properties

| Name | Summary |
|---|---|
| [skuCurrencyCode](sku-currency-code.md) | [androidJvm]<br>val [skuCurrencyCode](sku-currency-code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuLocalData](sku-local-data.md) | [androidJvm]<br>val [skuLocalData](sku-local-data.md): [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)? |
| [skuMonth](sku-month.md) | [androidJvm]<br>val [skuMonth](sku-month.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [skuSumMonth](sku-sum-month.md) | [androidJvm]<br>val [skuSumMonth](sku-sum-month.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |

## Functions

| Name | Summary |
|---|---|
| [toSkuSumMonthModel](../to-sku-sum-month-model.md) | [androidJvm]<br>fun [SkuSumMonthRelations](index.md).[toSkuSumMonthModel](../to-sku-sum-month-model.md)(): [SkuSumMonthModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-sum-month-model/index.md) |
