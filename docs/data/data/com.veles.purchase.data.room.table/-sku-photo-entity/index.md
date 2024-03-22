//[data](../../../index.md)/[com.veles.purchase.data.room.table](../index.md)/[SkuPhotoEntity](index.md)

# SkuPhotoEntity

[androidJvm]\
data class [SkuPhotoEntity](index.md)(val skuPhotoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), val skuPhotoUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html) = Uri.EMPTY, val skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

## Constructors

| | |
|---|---|
| [SkuPhotoEntity](-sku-photo-entity.md) | [androidJvm]<br>constructor(skuPhotoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), skuPhotoUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html) = Uri.EMPTY, skuId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [skuId](sku-id.md) | [androidJvm]<br>val [skuId](sku-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuPhotoId](sku-photo-id.md) | [androidJvm]<br>val [skuPhotoId](sku-photo-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [skuPhotoUri](sku-photo-uri.md) | [androidJvm]<br>val [skuPhotoUri](sku-photo-uri.md): [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](index.md#-1578325224%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [describeContents](index.md#-1578325224%2FFunctions%2F-70787932)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toSkuPhotoModel](../to-sku-photo-model.md) | [androidJvm]<br>fun [SkuPhotoEntity](index.md).[toSkuPhotoModel](../to-sku-photo-model.md)(): [SkuPhotoModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-photo-model/index.md) |
| [writeToParcel](index.md#-1754457655%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [writeToParcel](index.md#-1754457655%2FFunctions%2F-70787932)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
