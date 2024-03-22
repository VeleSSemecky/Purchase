//[data](../../../index.md)/[com.veles.purchase.data.repository.message](../index.md)/[NotificationMessageRepositoryImpl](index.md)

# NotificationMessageRepositoryImpl

[androidJvm]\
@Singleton

class [NotificationMessageRepositoryImpl](index.md)@Injectconstructor(notificationMessageService: [NotificationMessageService](../../com.veles.purchase.data.networking.service.message/-notification-message-service/index.md), firebaseFirestore: FirebaseFirestore, dataStore: [DataStore](../../com.veles.purchase.data.local.data/-data-store/index.md)) : [NotificationMessageRepository](../../../../domain/domain/com.veles.purchase.domain.repository.message/-notification-message-repository/index.md)

## Constructors

| | |
|---|---|
| [NotificationMessageRepositoryImpl](-notification-message-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(notificationMessageService: [NotificationMessageService](../../com.veles.purchase.data.networking.service.message/-notification-message-service/index.md), firebaseFirestore: FirebaseFirestore, dataStore: [DataStore](../../com.veles.purchase.data.local.data/-data-store/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [sendNotificationMessage](send-notification-message.md) | [androidJvm]<br>open suspend override fun [sendNotificationMessage](send-notification-message.md)(purchaseCollectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
