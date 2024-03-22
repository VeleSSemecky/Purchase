//[data](../../../index.md)/[com.veles.purchase.data.repository.sku](../index.md)/[SkuPhotoRepositoryImpl](index.md)

# SkuPhotoRepositoryImpl

[androidJvm]\
class [SkuPhotoRepositoryImpl](index.md)@Injectconstructor(skuPhotoDAO: [SkuPhotoDAO](../../com.veles.purchase.data.room.dao/-sku-photo-d-a-o/index.md)) : [SkuPhotoRepository](../../../../domain/domain/com.veles.purchase.domain.repository.sku/-sku-photo-repository/index.md)

## Constructors

| | |
|---|---|
| [SkuPhotoRepositoryImpl](-sku-photo-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(skuPhotoDAO: [SkuPhotoDAO](../../com.veles.purchase.data.room.dao/-sku-photo-d-a-o/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deletePhoto](delete-photo.md) | [androidJvm]<br>open suspend override fun [deletePhoto](delete-photo.md)(skuPhotoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [getSkuPhotoModelList](get-sku-photo-model-list.md) | [androidJvm]<br>open suspend override fun [getSkuPhotoModelList](get-sku-photo-model-list.md)(skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuPhotoModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-photo-model/index.md)&gt; |
