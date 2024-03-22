//[data](../../index.md)/[com.veles.purchase.data.model.fcm](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [DataModelData](-data-model-data/index.md) | [androidJvm]<br>data class [DataModelData](-data-model-data/index.md)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val tokenSender: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [NotificationMessageModelData](-notification-message-model-data/index.md) | [androidJvm]<br>data class [NotificationMessageModelData](-notification-message-model-data/index.md)(val registrationIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = arrayListOf(), val data: [DataModelData](-data-model-data/index.md)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [NotificationTokenModelData](-notification-token-model-data/index.md) | [androidJvm]<br>data class [NotificationTokenModelData](-notification-token-model-data/index.md)(val token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

## Functions

| Name | Summary |
|---|---|
| [toDataModel](to-data-model.md) | [androidJvm]<br>fun [DataModelData](-data-model-data/index.md).[toDataModel](to-data-model.md)(): [DataModel](../../../domain/domain/com.veles.purchase.domain.model.fcm/-data-model/index.md) |
| [toDataModelData](to-data-model-data.md) | [androidJvm]<br>fun [DataModel](../../../domain/domain/com.veles.purchase.domain.model.fcm/-data-model/index.md).[toDataModelData](to-data-model-data.md)(): [DataModelData](-data-model-data/index.md) |
| [toNotificationMessageModel](to-notification-message-model.md) | [androidJvm]<br>fun [NotificationMessageModelData](-notification-message-model-data/index.md).[toNotificationMessageModel](to-notification-message-model.md)(): [NotificationMessageModel](../../../domain/domain/com.veles.purchase.domain.model.fcm/-notification-message-model/index.md) |
| [toNotificationMessageModelData](to-notification-message-model-data.md) | [androidJvm]<br>fun [NotificationMessageModel](../../../domain/domain/com.veles.purchase.domain.model.fcm/-notification-message-model/index.md).[toNotificationMessageModelData](to-notification-message-model-data.md)(): [NotificationMessageModelData](-notification-message-model-data/index.md) |
