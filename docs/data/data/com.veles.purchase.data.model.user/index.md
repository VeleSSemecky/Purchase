//[data](../../index.md)/[com.veles.purchase.data.model.user](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [UserPurchaseModelData](-user-purchase-model-data/index.md) | [androidJvm]<br>data class [UserPurchaseModelData](-user-purchase-model-data/index.md)(val uid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val providerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = dashString(), val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = dashString(), val phoneNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = dashString(), val fcmToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = emptyString(), val photoUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = emptyString()) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

## Functions

| Name | Summary |
|---|---|
| [createUserPurchase](create-user-purchase.md) | [androidJvm]<br>fun FirebaseUser.[createUserPurchase](create-user-purchase.md)(): [UserPurchaseModelData](-user-purchase-model-data/index.md) |
| [toUserPurchaseModel](to-user-purchase-model.md) | [androidJvm]<br>fun [UserPurchaseModelData](-user-purchase-model-data/index.md).[toUserPurchaseModel](to-user-purchase-model.md)(): [UserPurchaseModel](../../../domain/domain/com.veles.purchase.domain.model.user/-user-purchase-model/index.md) |
| [toUserPurchaseModelData](to-user-purchase-model-data.md) | [androidJvm]<br>fun [UserPurchaseModel](../../../domain/domain/com.veles.purchase.domain.model.user/-user-purchase-model/index.md).[toUserPurchaseModelData](to-user-purchase-model-data.md)(): [UserPurchaseModelData](-user-purchase-model-data/index.md) |
