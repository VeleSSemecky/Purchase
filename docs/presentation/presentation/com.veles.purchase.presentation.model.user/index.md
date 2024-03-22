//[presentation](../../index.md)/[com.veles.purchase.presentation.model.user](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [UserCheckedUI](-user-checked-u-i/index.md) | [androidJvm]<br>data class [UserCheckedUI](-user-checked-u-i/index.md)(val isCheck: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val userPurchase: [UserPurchaseModelUI](-user-purchase-model-u-i/index.md)) |
| [UserPurchaseModelUI](-user-purchase-model-u-i/index.md) | [androidJvm]<br>data class [UserPurchaseModelUI](-user-purchase-model-u-i/index.md)(val uid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val providerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;-&quot;, val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;-&quot;, val phoneNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;-&quot;, val fcmToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;, val photoUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

## Functions

| Name | Summary |
|---|---|
| [toUserPurchaseModel](to-user-purchase-model.md) | [androidJvm]<br>fun [UserPurchaseModelUI](-user-purchase-model-u-i/index.md).[toUserPurchaseModel](to-user-purchase-model.md)(): [UserPurchaseModel](../../../domain/domain/com.veles.purchase.domain.model.user/-user-purchase-model/index.md) |
| [toUserPurchaseModelUI](to-user-purchase-model-u-i.md) | [androidJvm]<br>fun [UserPurchaseModel](../../../domain/domain/com.veles.purchase.domain.model.user/-user-purchase-model/index.md).[toUserPurchaseModelUI](to-user-purchase-model-u-i.md)(): [UserPurchaseModelUI](-user-purchase-model-u-i/index.md) |
