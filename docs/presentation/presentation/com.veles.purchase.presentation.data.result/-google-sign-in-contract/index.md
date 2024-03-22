//[presentation](../../../index.md)/[com.veles.purchase.presentation.data.result](../index.md)/[GoogleSignInContract](index.md)

# GoogleSignInContract

[androidJvm]\
class [GoogleSignInContract](index.md) : [ActivityResultContract](https://developer.android.com/reference/kotlin/androidx/activity/result/contract/ActivityResultContract.html)&lt;[Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt;

## Constructors

| | |
|---|---|
| [GoogleSignInContract](-google-sign-in-contract.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [createIntent](create-intent.md) | [androidJvm]<br>open override fun [createIntent](create-intent.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), input: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)): [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html) |
| [getSynchronousResult](index.md#1094172138%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [getSynchronousResult](index.md#1094172138%2FFunctions%2F-646359276)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), input: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)): [ActivityResultContract.SynchronousResult](https://developer.android.com/reference/kotlin/androidx/activity/result/contract/ActivityResultContract.SynchronousResult.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt;? |
| [parseResult](parse-result.md) | [androidJvm]<br>open override fun [parseResult](parse-result.md)(resultCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), intent: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
