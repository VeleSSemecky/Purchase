//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.sku](../index.md)/[GetSkuUseCase](index.md)

# GetSkuUseCase

[jvm]\
class [GetSkuUseCase](index.md)@Injectconstructor(skuDAO: [SkuRepository](../../com.veles.purchase.domain.repository.sku/-sku-repository/index.md))

## Constructors

| | |
|---|---|
| [GetSkuUseCase](-get-sku-use-case.md) | [jvm]<br>@Inject<br>constructor(skuDAO: [SkuRepository](../../com.veles.purchase.domain.repository.sku/-sku-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getSkuModel](get-sku-model.md) | [jvm]<br>suspend fun [getSkuModel](get-sku-model.md)(skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SkuModel](../../com.veles.purchase.domain.model/-sku-model/index.md)? |
| [getSkuModelList](get-sku-model-list.md) | [jvm]<br>suspend fun [getSkuModelList](get-sku-model-list.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuModel](../../com.veles.purchase.domain.model/-sku-model/index.md)&gt; |
