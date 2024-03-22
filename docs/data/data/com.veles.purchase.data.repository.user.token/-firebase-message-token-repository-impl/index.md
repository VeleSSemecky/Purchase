//[data](../../../index.md)/[com.veles.purchase.data.repository.user.token](../index.md)/[FirebaseMessageTokenRepositoryImpl](index.md)

# FirebaseMessageTokenRepositoryImpl

[androidJvm]\
@Singleton

class [FirebaseMessageTokenRepositoryImpl](index.md)@Injectconstructor(firebaseFirestore: FirebaseFirestore) : [FirebaseMessageTokenRepository](../../../../domain/domain/com.veles.purchase.domain.repository.user/-firebase-message-token-repository/index.md)

## Constructors

| | |
|---|---|
| [FirebaseMessageTokenRepositoryImpl](-firebase-message-token-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(firebaseFirestore: FirebaseFirestore) |

## Functions

| Name | Summary |
|---|---|
| [sendMessageToken](send-message-token.md) | [androidJvm]<br>open suspend override fun [sendMessageToken](send-message-token.md)(userUid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), messageToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Void](https://developer.android.com/reference/kotlin/java/lang/Void.html) |
