//[presentation](../../../index.md)/[com.veles.purchase.presentation.data.result](../index.md)/[RequestUriContract](index.md)

# RequestUriContract

[androidJvm]\
class [RequestUriContract](index.md) : [ActivityResultContract](https://developer.android.com/reference/kotlin/androidx/activity/result/contract/ActivityResultContract.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)?&gt;

## Constructors

| | |
|---|---|
| [RequestUriContract](-request-uri-contract.md) | [androidJvm]<br>constructor() |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [createIntent](create-intent.md) | [androidJvm]<br>open override fun [createIntent](create-intent.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), input: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html) |
| [getSynchronousResult](index.md#1791132063%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [getSynchronousResult](index.md#1791132063%2FFunctions%2F-646359276)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), input: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [ActivityResultContract.SynchronousResult](https://developer.android.com/reference/kotlin/androidx/activity/result/contract/ActivityResultContract.SynchronousResult.html)&lt;[Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)?&gt;? |
| [parseResult](parse-result.md) | [androidJvm]<br>open override fun [parseResult](parse-result.md)(resultCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), intent: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)?): [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)? |
