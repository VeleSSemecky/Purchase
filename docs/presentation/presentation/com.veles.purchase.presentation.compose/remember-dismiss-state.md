//[presentation](../../index.md)/[com.veles.purchase.presentation.compose](index.md)/[rememberDismissState](remember-dismiss-state.md)

# rememberDismissState

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [rememberDismissState](remember-dismiss-state.md)(initialValue: [DismissValue](-dismiss-value/index.md) = DismissValue.Default, confirmStateChange: ([DismissValue](-dismiss-value/index.md)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = { true }): [DismissState](-dismiss-state/index.md)

Create and [remember](https://developer.android.com/reference/kotlin/androidx/compose/runtime/package-summary.html) a [DismissState](-dismiss-state/index.md).

#### Parameters

androidJvm

| | |
|---|---|
| initialValue | The initial value of the state. |
| confirmStateChange | Optional callback invoked to confirm or veto a pending state change. |
