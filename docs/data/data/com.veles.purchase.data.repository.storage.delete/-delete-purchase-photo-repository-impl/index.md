//[data](../../../index.md)/[com.veles.purchase.data.repository.storage.delete](../index.md)/[DeletePurchasePhotoRepositoryImpl](index.md)

# DeletePurchasePhotoRepositoryImpl

[androidJvm]\
class [DeletePurchasePhotoRepositoryImpl](index.md)@Injectconstructor(contentResolver: [ContentResolver](https://developer.android.com/reference/kotlin/android/content/ContentResolver.html), storage: FirebaseStorage, logger: [Logger](../../../../domain/domain/com.veles.purchase.domain.core.loger/-logger/index.md)) : [DeletePurchasePhotoRepository](../../../../domain/domain/com.veles.purchase.domain.repository.storage/-delete-purchase-photo-repository/index.md)

## Constructors

| | |
|---|---|
| [DeletePurchasePhotoRepositoryImpl](-delete-purchase-photo-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(contentResolver: [ContentResolver](https://developer.android.com/reference/kotlin/android/content/ContentResolver.html), storage: FirebaseStorage, logger: [Logger](../../../../domain/domain/com.veles.purchase.domain.core.loger/-logger/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deletePhoto](delete-photo.md) | [androidJvm]<br>open suspend override fun [deletePhoto](delete-photo.md)(purchasePhotoModel: [PurchasePhotoModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-photo-model/index.md)) |
| [deletePhotos](delete-photos.md) | [androidJvm]<br>open suspend override fun [deletePhotos](delete-photos.md)(purchaseId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), listImage: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-photo-model/index.md)&gt;) |
