//[domain](../../../index.md)/[com.veles.purchase.domain.model](../index.md)/[SkuModel](index.md)

# SkuModel

[jvm]\
data class [SkuModel](index.md)(val skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val skuLocalData: [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html) = LocalDateTime.now(), val skuName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuComment: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuPrice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuCurrencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Currency.getInstance(Locale.getDefault()).currencyCode)

## Constructors

| | |
|---|---|
| [SkuModel](-sku-model.md) | [jvm]<br>constructor(skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), skuLocalData: [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html) = LocalDateTime.now(), skuName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), skuComment: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), skuPrice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), skuCurrencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Currency.getInstance(Locale.getDefault()).currencyCode) |

## Properties

| Name | Summary |
|---|---|
| [skuComment](sku-comment.md) | [jvm]<br>val [skuComment](sku-comment.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuCurrencyCode](sku-currency-code.md) | [jvm]<br>val [skuCurrencyCode](sku-currency-code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuId](sku-id.md) | [jvm]<br>val [skuId](sku-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuLocalData](sku-local-data.md) | [jvm]<br>val [skuLocalData](sku-local-data.md): [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html) |
| [skuName](sku-name.md) | [jvm]<br>val [skuName](sku-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuPrice](sku-price.md) | [jvm]<br>val [skuPrice](sku-price.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
