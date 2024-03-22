//[domain](../../index.md)/[com.veles.purchase.domain.core](index.md)/[suspendCancellableCoroutineWithTimeout](suspend-cancellable-coroutine-with-timeout.md)

# suspendCancellableCoroutineWithTimeout

[jvm]\
inline suspend fun &lt;[T](suspend-cancellable-coroutine-with-timeout.md)&gt; [suspendCancellableCoroutineWithTimeout](suspend-cancellable-coroutine-with-timeout.md)(timeout: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = TimeUnit.SECONDS.toMillis(5.toLong()), crossinline block: suspend () -&gt; [T](suspend-cancellable-coroutine-with-timeout.md)): [T](suspend-cancellable-coroutine-with-timeout.md)
