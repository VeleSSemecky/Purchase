//[presentation](../../index.md)/[com.veles.purchase.presentation.extensions](index.md)/[launchOnError](launch-on-error.md)

# launchOnError

[androidJvm]\
fun CoroutineScope.[launchOnError](launch-on-error.md)(context: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) = EmptyCoroutineContext, start: CoroutineStart = CoroutineStart.DEFAULT, catch: suspend CoroutineScope.([Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = { }, block: suspend CoroutineScope.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Job
