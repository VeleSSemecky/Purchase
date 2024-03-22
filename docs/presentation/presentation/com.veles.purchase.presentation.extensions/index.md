//[presentation](../../index.md)/[com.veles.purchase.presentation.extensions](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [LocaleCurrency](-locale-currency/index.md) | [androidJvm]<br>object [LocaleCurrency](-locale-currency/index.md) |

## Functions

| Name | Summary |
|---|---|
| [findParentNavController](find-parent-nav-controller.md) | [androidJvm]<br>fun [Fragment](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment.html).[findParentNavController](find-parent-nav-controller.md)(): [NavController](https://developer.android.com/reference/kotlin/androidx/navigation/NavController.html) |
| [launchOnError](launch-on-error.md) | [androidJvm]<br>fun CoroutineScope.[launchOnError](launch-on-error.md)(context: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) = EmptyCoroutineContext, start: CoroutineStart = CoroutineStart.DEFAULT, catch: suspend CoroutineScope.([Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = { }, block: suspend CoroutineScope.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Job |
| [launchRepeatOnLifecycle](launch-repeat-on-lifecycle.md) | [androidJvm]<br>fun [Fragment](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment.html).[launchRepeatOnLifecycle](launch-repeat-on-lifecycle.md)(state: [Lifecycle.State](https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle.State.html), context: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) = EmptyCoroutineContext, start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Job |
| [mapStateIn](map-state-in.md) | [androidJvm]<br>inline fun &lt;[T](map-state-in.md), [R](map-state-in.md)&gt; StateFlow&lt;[T](map-state-in.md)&gt;.[mapStateIn](map-state-in.md)(scope: CoroutineScope, crossinline transform: (value: [T](map-state-in.md)) -&gt; [R](map-state-in.md)): StateFlow&lt;[R](map-state-in.md)&gt; |
| [onCreateComposeView](on-create-compose-view.md) | [androidJvm]<br>fun [Fragment](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment.html).[onCreateComposeView](on-create-compose-view.md)(content: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [ComposeView](https://developer.android.com/reference/kotlin/androidx/compose/ui/platform/ComposeView.html) |
| [setPictureInPictureParams](set-picture-in-picture-params.md) | [androidJvm]<br>fun [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html).[setPictureInPictureParams](set-picture-in-picture-params.md)(params: [PictureInPictureParams.Builder](https://developer.android.com/reference/kotlin/android/app/PictureInPictureParams.Builder.html), vararg remoteActions: [VideoRemoteAction](../com.veles.purchase.presentation.presentation.mvvm.pip/-video-remote-action/index.md)) |
| [toCurrency](to-currency.md) | [androidJvm]<br>fun [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).[toCurrency](to-currency.md)(): [Currency](https://developer.android.com/reference/kotlin/java/util/Currency.html) |
| [toLocalDateTime](to-local-date-time.md) | [androidJvm]<br>fun [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html).[toLocalDateTime](to-local-date-time.md)(): [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html) |
