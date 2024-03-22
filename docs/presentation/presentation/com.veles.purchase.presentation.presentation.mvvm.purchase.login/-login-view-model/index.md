//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.purchase.login](../index.md)/[LoginViewModel](index.md)

# LoginViewModel

[androidJvm]\
class [LoginViewModel](index.md)@Injectconstructor(router: [Router](../../com.veles.purchase.presentation.base.mvvm.navigation/-router/index.md), googleSignInClient: GoogleSignInClient, firebaseSetUserUseCase: [FirebaseSetUserUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase/-firebase-set-user-use-case/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [LoginViewModel](-login-view-model.md) | [androidJvm]<br>@Inject<br>constructor(router: [Router](../../com.veles.purchase.presentation.base.mvvm.navigation/-router/index.md), googleSignInClient: GoogleSignInClient, firebaseSetUserUseCase: [FirebaseSetUserUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase/-firebase-set-user-use-case/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [firebaseAuthWithGoogle](firebase-auth-with-google.md) | [androidJvm]<br>fun [firebaseAuthWithGoogle](firebase-auth-with-google.md)(idToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
| [googleSignInIntent](google-sign-in-intent.md) | [androidJvm]<br>fun [googleSignInIntent](google-sign-in-intent.md)(): [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html) |
