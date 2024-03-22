//[data](../../../index.md)/[com.veles.purchase.data.room.dao](../index.md)/[SkuPhotoDAO](index.md)

# SkuPhotoDAO

[androidJvm]\
interface [SkuPhotoDAO](index.md)

## Functions

| Name | Summary |
|---|---|
| [deleteSkuPhotoEntityWithPhotoId](delete-sku-photo-entity-with-photo-id.md) | [androidJvm]<br>abstract suspend fun [deleteSkuPhotoEntityWithPhotoId](delete-sku-photo-entity-with-photo-id.md)(skuPhotoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [deleteSkuPhotoEntityWithSkuId](delete-sku-photo-entity-with-sku-id.md) | [androidJvm]<br>abstract suspend fun [deleteSkuPhotoEntityWithSkuId](delete-sku-photo-entity-with-sku-id.md)(skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [getSkuPhotoEntity](get-sku-photo-entity.md) | [androidJvm]<br>abstract suspend fun [getSkuPhotoEntity](get-sku-photo-entity.md)(skuPhotoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SkuPhotoEntity](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md)? |
| [getSkuPhotoEntityList](get-sku-photo-entity-list.md) | [androidJvm]<br>abstract suspend fun [getSkuPhotoEntityList](get-sku-photo-entity-list.md)(skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuPhotoEntity](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md)&gt; |
| [insert](insert.md) | [androidJvm]<br>abstract suspend fun [insert](insert.md)(entity: [SkuPhotoEntity](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md))<br>abstract suspend fun [insert](insert.md)(entity: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuPhotoEntity](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md)&gt;) |
