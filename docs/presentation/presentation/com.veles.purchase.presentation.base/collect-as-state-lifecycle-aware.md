//[presentation](../../index.md)/[com.veles.purchase.presentation.base](index.md)/[collectAsStateLifecycleAware](collect-as-state-lifecycle-aware.md)

# collectAsStateLifecycleAware

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun &lt;[T](collect-as-state-lifecycle-aware.md) : [R](collect-as-state-lifecycle-aware.md), [R](collect-as-state-lifecycle-aware.md)&gt; Flow&lt;[T](collect-as-state-lifecycle-aware.md)&gt;.[collectAsStateLifecycleAware](collect-as-state-lifecycle-aware.md)(initial: [R](collect-as-state-lifecycle-aware.md), context: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) = EmptyCoroutineContext, minActiveState: [Lifecycle.State](https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle.State.html) = Lifecycle.State.STARTED): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[R](collect-as-state-lifecycle-aware.md)&gt;

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun &lt;[T](collect-as-state-lifecycle-aware.md) : [R](collect-as-state-lifecycle-aware.md), [R](collect-as-state-lifecycle-aware.md)&gt; StateFlow&lt;[T](collect-as-state-lifecycle-aware.md)&gt;.[collectAsStateLifecycleAware](collect-as-state-lifecycle-aware.md)(initial: [R](collect-as-state-lifecycle-aware.md) = value, context: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) = EmptyCoroutineContext, minActiveState: [Lifecycle.State](https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle.State.html) = Lifecycle.State.STARTED): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[R](collect-as-state-lifecycle-aware.md)&gt;
