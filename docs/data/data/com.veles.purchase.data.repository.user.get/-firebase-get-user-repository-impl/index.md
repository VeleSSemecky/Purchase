//[data](../../../index.md)/[com.veles.purchase.data.repository.user.get](../index.md)/[FirebaseGetUserRepositoryImpl](index.md)

# FirebaseGetUserRepositoryImpl

[androidJvm]\
@Singleton

class [FirebaseGetUserRepositoryImpl](index.md)@Injectconstructor(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth) : [FirebaseGetUserRepository](../../../../domain/domain/com.veles.purchase.domain.repository.user/-firebase-get-user-repository/index.md)

## Constructors

| | |
|---|---|
| [FirebaseGetUserRepositoryImpl](-firebase-get-user-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth) |

## Functions

| Name | Summary |
|---|---|
| [apiFirebaseFirestore](api-firebase-firestore.md) | [androidJvm]<br>open suspend override fun [apiFirebaseFirestore](api-firebase-firestore.md)(): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserPurchaseModel](../../../../domain/domain/com.veles.purchase.domain.model.user/-user-purchase-model/index.md)&gt;&gt; |
| [apiGetUserPurchase](api-get-user-purchase.md) | [androidJvm]<br>open suspend override fun [apiGetUserPurchase](api-get-user-purchase.md)(): [UserPurchaseModel](../../../../domain/domain/com.veles.purchase.domain.model.user/-user-purchase-model/index.md)? |
| [isNeedLogin](is-need-login.md) | [androidJvm]<br>open override fun [isNeedLogin](is-need-login.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
