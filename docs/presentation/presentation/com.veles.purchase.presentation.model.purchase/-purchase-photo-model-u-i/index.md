//[presentation](../../../index.md)/[com.veles.purchase.presentation.model.purchase](../index.md)/[PurchasePhotoModelUI](index.md)

# PurchasePhotoModelUI

[androidJvm]\
data class [PurchasePhotoModelUI](index.md)(val purchaseId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val purchasePhotoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), val purchasePhotoUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Uri.EMPTY.toString(), val status: [PhotoStatus](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-photo-status/index.md) = PhotoStatus.LOCAL) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

## Constructors

| | |
|---|---|
| [PurchasePhotoModelUI](-purchase-photo-model-u-i.md) | [androidJvm]<br>constructor(purchaseId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), purchasePhotoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), purchasePhotoUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Uri.EMPTY.toString(), status: [PhotoStatus](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-photo-status/index.md) = PhotoStatus.LOCAL) |

## Properties

| Name | Summary |
|---|---|
| [purchaseId](purchase-id.md) | [androidJvm]<br>val [purchaseId](purchase-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [purchasePhotoId](purchase-photo-id.md) | [androidJvm]<br>val [purchasePhotoId](purchase-photo-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [purchasePhotoUri](purchase-photo-uri.md) | [androidJvm]<br>val [purchasePhotoUri](purchase-photo-uri.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [status](status.md) | [androidJvm]<br>val [status](status.md): [PhotoStatus](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-photo-status/index.md) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1578325224%2FFunctions%2F-646359276) | [androidJvm]<br>abstract fun [describeContents](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1578325224%2FFunctions%2F-646359276)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toPurchasePhotoModel](../to-purchase-photo-model.md) | [androidJvm]<br>fun [PurchasePhotoModelUI](index.md).[toPurchasePhotoModel](../to-purchase-photo-model.md)(): [PurchasePhotoModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-photo-model/index.md) |
| [writeToParcel](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1754457655%2FFunctions%2F-646359276) | [androidJvm]<br>abstract fun [writeToParcel](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1754457655%2FFunctions%2F-646359276)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
