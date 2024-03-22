//[presentation](../../index.md)/[com.veles.purchase.presentation.base](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AppLifecycleObserver](-app-lifecycle-observer/index.md) | [androidJvm]<br>class [AppLifecycleObserver](-app-lifecycle-observer/index.md)@Injectconstructor(dataStore: [DataStore](../../../data/data/com.veles.purchase.data.local.data/-data-store/index.md)) : [DefaultLifecycleObserver](https://developer.android.com/reference/kotlin/androidx/lifecycle/DefaultLifecycleObserver.html) |

## Functions

| Name | Summary |
|---|---|
| [collectAsStateLifecycleAware](collect-as-state-lifecycle-aware.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun &lt;[T](collect-as-state-lifecycle-aware.md) : [R](collect-as-state-lifecycle-aware.md), [R](collect-as-state-lifecycle-aware.md)&gt; Flow&lt;[T](collect-as-state-lifecycle-aware.md)&gt;.[collectAsStateLifecycleAware](collect-as-state-lifecycle-aware.md)(initial: [R](collect-as-state-lifecycle-aware.md), context: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) = EmptyCoroutineContext, minActiveState: [Lifecycle.State](https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle.State.html) = Lifecycle.State.STARTED): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[R](collect-as-state-lifecycle-aware.md)&gt;<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun &lt;[T](collect-as-state-lifecycle-aware.md) : [R](collect-as-state-lifecycle-aware.md), [R](collect-as-state-lifecycle-aware.md)&gt; StateFlow&lt;[T](collect-as-state-lifecycle-aware.md)&gt;.[collectAsStateLifecycleAware](collect-as-state-lifecycle-aware.md)(initial: [R](collect-as-state-lifecycle-aware.md) = value, context: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) = EmptyCoroutineContext, minActiveState: [Lifecycle.State](https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle.State.html) = Lifecycle.State.STARTED): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[R](collect-as-state-lifecycle-aware.md)&gt; |
| [rememberFlow](remember-flow.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun &lt;[T](remember-flow.md)&gt; [rememberFlow](remember-flow.md)(flow: Flow&lt;[T](remember-flow.md)&gt;, lifecycleOwner: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html) = LocalLifecycleOwner.current, minActiveState: [Lifecycle.State](https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle.State.html) = Lifecycle.State.STARTED): Flow&lt;[T](remember-flow.md)&gt; |
