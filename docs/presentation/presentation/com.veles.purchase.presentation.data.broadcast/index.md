//[presentation](../../index.md)/[com.veles.purchase.presentation.data.broadcast](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [Callback](-callback/index.md) | [androidJvm]<br>fun interface [Callback](-callback/index.md) |
| [CallbackBroadcastReceiver](-callback-broadcast-receiver/index.md) | [androidJvm]<br>class [CallbackBroadcastReceiver](-callback-broadcast-receiver/index.md)(callback: [Callback](-callback/index.md)) : [BroadcastReceiver](https://developer.android.com/reference/kotlin/android/content/BroadcastReceiver.html) |
| [RemoteActionBroadcastReceiver](-remote-action-broadcast-receiver/index.md) | [androidJvm]<br>class [RemoteActionBroadcastReceiver](-remote-action-broadcast-receiver/index.md)@Injectconstructor(val context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |

## Functions

| Name | Summary |
|---|---|
| [callbackBroadcastReceiver](callback-broadcast-receiver.md) | [androidJvm]<br>fun [callbackBroadcastReceiver](callback-broadcast-receiver.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), externalScope: CoroutineScope, intentFilter: [IntentFilter](https://developer.android.com/reference/kotlin/android/content/IntentFilter.html)): SharedFlow&lt;[Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)?&gt; |
