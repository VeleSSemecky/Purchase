//[domain](../../../index.md)/[com.veles.purchase.domain.repository.sku](../index.md)/[SkuRepository](index.md)

# SkuRepository

[jvm]\
interface [SkuRepository](index.md)

## Functions

| Name | Summary |
|---|---|
| [delete](delete.md) | [jvm]<br>abstract suspend fun [delete](delete.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [getSkuEntityList](get-sku-entity-list.md) | [jvm]<br>abstract suspend fun [getSkuEntityList](get-sku-entity-list.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuModel](../../com.veles.purchase.domain.model/-sku-model/index.md)&gt; |
| [getSkuModel](get-sku-model.md) | [jvm]<br>abstract suspend fun [getSkuModel](get-sku-model.md)(skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SkuModel](../../com.veles.purchase.domain.model/-sku-model/index.md)? |
| [getSkuSumMonthList](get-sku-sum-month-list.md) | [jvm]<br>abstract suspend fun [getSkuSumMonthList](get-sku-sum-month-list.md)(year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), month: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuSumMonthModel](../../com.veles.purchase.domain.model/-sku-sum-month-model/index.md)&gt; |
| [insert](insert.md) | [jvm]<br>abstract suspend fun [insert](insert.md)(skuModel: [SkuModel](../../com.veles.purchase.domain.model/-sku-model/index.md), skuPhotoModelList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuPhotoModel](../../com.veles.purchase.domain.model/-sku-photo-model/index.md)&gt;) |
