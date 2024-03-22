//[data](../../../index.md)/[com.veles.purchase.data.repository.sku](../index.md)/[SkuRepositoryImpl](index.md)

# SkuRepositoryImpl

[androidJvm]\
class [SkuRepositoryImpl](index.md)@Injectconstructor(skuDAO: [SkuDAO](../../com.veles.purchase.data.room.dao/-sku-d-a-o/index.md)) : [SkuRepository](../../../../domain/domain/com.veles.purchase.domain.repository.sku/-sku-repository/index.md)

## Constructors

| | |
|---|---|
| [SkuRepositoryImpl](-sku-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(skuDAO: [SkuDAO](../../com.veles.purchase.data.room.dao/-sku-d-a-o/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [delete](delete.md) | [androidJvm]<br>open suspend override fun [delete](delete.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [getSkuEntityList](get-sku-entity-list.md) | [androidJvm]<br>open suspend override fun [getSkuEntityList](get-sku-entity-list.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-model/index.md)&gt; |
| [getSkuModel](get-sku-model.md) | [androidJvm]<br>open suspend override fun [getSkuModel](get-sku-model.md)(skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SkuModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-model/index.md)? |
| [getSkuSumMonthList](get-sku-sum-month-list.md) | [androidJvm]<br>open suspend override fun [getSkuSumMonthList](get-sku-sum-month-list.md)(year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), month: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuSumMonthModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-sum-month-model/index.md)&gt; |
| [insert](insert.md) | [androidJvm]<br>open suspend override fun [insert](insert.md)(skuModel: [SkuModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-model/index.md), skuPhotoModelList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuPhotoModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-photo-model/index.md)&gt;) |
