//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.pip](../index.md)/[VideoRemoteAction](index.md)

# VideoRemoteAction

sealed interface [VideoRemoteAction](index.md)

#### Inheritors

| |
|---|
| [PauseRemoteAction](../-pause-remote-action/index.md) |
| [StopRemoteAction](../-stop-remote-action/index.md) |
| [PlayRemoteAction](../-play-remote-action/index.md) |
| [SwitchRemoteAction](../-switch-remote-action/index.md) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [action](action.md) | [androidJvm]<br>abstract val [action](action.md): [RemoteAction](https://developer.android.com/reference/kotlin/android/app/RemoteAction.html) |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [androidJvm]<br>open operator fun [invoke](invoke.md)(): [RemoteAction](https://developer.android.com/reference/kotlin/android/app/RemoteAction.html) |
| [onRemoteAction](on-remote-action.md) | [androidJvm]<br>open fun [onRemoteAction](on-remote-action.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), @[DrawableRes](https://developer.android.com/reference/kotlin/androidx/annotation/DrawableRes.html)resId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), videoControl: [VideoControl](../-video-control/index.md)): [RemoteAction](https://developer.android.com/reference/kotlin/android/app/RemoteAction.html) |
