//[presentation](../../index.md)/[com.veles.purchase.presentation.base](index.md)/[rememberFlow](remember-flow.md)

# rememberFlow

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun &lt;[T](remember-flow.md)&gt; [rememberFlow](remember-flow.md)(flow: Flow&lt;[T](remember-flow.md)&gt;, lifecycleOwner: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html) = LocalLifecycleOwner.current, minActiveState: [Lifecycle.State](https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle.State.html) = Lifecycle.State.STARTED): Flow&lt;[T](remember-flow.md)&gt;
