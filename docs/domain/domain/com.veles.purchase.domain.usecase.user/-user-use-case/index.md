//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.user](../index.md)/[UserUseCase](index.md)

# UserUseCase

[jvm]\
class [UserUseCase](index.md)@Injectconstructor(firebaseGetUserRepository: [FirebaseGetUserRepository](../../com.veles.purchase.domain.repository.user/-firebase-get-user-repository/index.md))

## Constructors

| | |
|---|---|
| [UserUseCase](-user-use-case.md) | [jvm]<br>@Inject<br>constructor(firebaseGetUserRepository: [FirebaseGetUserRepository](../../com.veles.purchase.domain.repository.user/-firebase-get-user-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getUserPurchase](get-user-purchase.md) | [jvm]<br>suspend fun [getUserPurchase](get-user-purchase.md)(): [UserPurchaseModel](../../com.veles.purchase.domain.model.user/-user-purchase-model/index.md)? |
| [invoke](invoke.md) | [jvm]<br>suspend operator fun [invoke](invoke.md)(): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserPurchaseModel](../../com.veles.purchase.domain.model.user/-user-purchase-model/index.md)&gt;&gt; |
| [isNeedLogin](is-need-login.md) | [jvm]<br>fun [isNeedLogin](is-need-login.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
