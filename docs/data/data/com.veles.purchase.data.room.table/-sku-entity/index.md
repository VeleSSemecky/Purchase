//[data](../../../index.md)/[com.veles.purchase.data.room.table](../index.md)/[SkuEntity](index.md)

# SkuEntity

[androidJvm]\
data class [SkuEntity](index.md)(val skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), val skuLocalData: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html) = LocalDateTime.now(), val skuName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuComment: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuPrice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuCurrencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Currency.getInstance(Locale.getDefault()).currencyCode)

## Constructors

| | |
|---|---|
| [SkuEntity](-sku-entity.md) | [androidJvm]<br>constructor(skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), skuLocalData: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html) = LocalDateTime.now(), skuName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), skuComment: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), skuPrice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), skuCurrencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Currency.getInstance(Locale.getDefault()).currencyCode) |

## Properties

| Name | Summary |
|---|---|
| [skuComment](sku-comment.md) | [androidJvm]<br>val [skuComment](sku-comment.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuCurrencyCode](sku-currency-code.md) | [androidJvm]<br>val [skuCurrencyCode](sku-currency-code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuId](sku-id.md) | [androidJvm]<br>val [skuId](sku-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuLocalData](sku-local-data.md) | [androidJvm]<br>val [skuLocalData](sku-local-data.md): [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html) |
| [skuName](sku-name.md) | [androidJvm]<br>val [skuName](sku-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuPrice](sku-price.md) | [androidJvm]<br>val [skuPrice](sku-price.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [toSkuModel](../to-sku-model.md) | [androidJvm]<br>fun [SkuEntity](index.md).[toSkuModel](../to-sku-model.md)(): [SkuModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-model/index.md) |
