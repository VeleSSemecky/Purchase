//[domain](../../index.md)/[com.veles.purchase.domain.model](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [SkuModel](-sku-model/index.md) | [jvm]<br>data class [SkuModel](-sku-model/index.md)(val skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val skuLocalData: [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html) = LocalDateTime.now(), val skuName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuComment: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuPrice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuCurrencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Currency.getInstance(Locale.getDefault()).currencyCode) |
| [SkuPhotoModel](-sku-photo-model/index.md) | [jvm]<br>data class [SkuPhotoModel](-sku-photo-model/index.md)(val skuPhotoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val skuPhotoUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [SkuSumMonthModel](-sku-sum-month-model/index.md) | [jvm]<br>data class [SkuSumMonthModel](-sku-sum-month-model/index.md)(val skuSumMonth: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val skuMonth: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val skuLocalData: [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html)?, val skuCurrencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
