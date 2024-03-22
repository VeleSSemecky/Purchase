//[presentation](../../../index.md)/[com.veles.purchase.presentation.data.result](../index.md)/[RequestPermissionCameraContract](index.md)

# RequestPermissionCameraContract

[androidJvm]\
class [RequestPermissionCameraContract](index.md) : [ActivityResultContract](https://developer.android.com/reference/kotlin/androidx/activity/result/contract/ActivityResultContract.html)&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;

## Constructors

| | |
|---|---|
| [RequestPermissionCameraContract](-request-permission-camera-contract.md) | [androidJvm]<br>constructor() |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [createIntent](create-intent.md) | [androidJvm]<br>open override fun [createIntent](create-intent.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), input: [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html) |
| [getSynchronousResult](get-synchronous-result.md) | [androidJvm]<br>open override fun [getSynchronousResult](get-synchronous-result.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), input: [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [ActivityResultContract.SynchronousResult](https://developer.android.com/reference/kotlin/androidx/activity/result/contract/ActivityResultContract.SynchronousResult.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;? |
| [parseResult](parse-result.md) | [androidJvm]<br>open override fun [parseResult](parse-result.md)(resultCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), intent: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
