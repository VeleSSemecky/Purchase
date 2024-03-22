//[domain](../../index.md)/[com.veles.purchase.domain.core.extensions](index.md)

# Package-level declarations

## Functions

| Name | Summary |
|---|---|
| [add](add.md) | [jvm]<br>fun &lt;[T](add.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](add.md)&gt;.[add](add.md)(element: [T](add.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](add.md)&gt; |
| [change](change.md) | [jvm]<br>suspend fun &lt;[T](change.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; MutableStateFlow&lt;[T](change.md)&gt;.[change](change.md)(changeValue: ([T](change.md)) -&gt; [T](change.md))<br>inline fun &lt;[T](change.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; MutableStateFlow&lt;[T](change.md)&gt;.[change](change.md)(coroutineScope: CoroutineScope, crossinline changeValue: ([T](change.md)) -&gt; [T](change.md)): Job |
| [emitNotNull](emit-not-null.md) | [jvm]<br>suspend fun &lt;[T](emit-not-null.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; MutableStateFlow&lt;[T](emit-not-null.md)&gt;.[emitNotNull](emit-not-null.md)(t: [T](emit-not-null.md)?) |
| [invoke](invoke.md) | [jvm]<br>operator fun &lt;[T](invoke.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; StateFlow&lt;[T](invoke.md)&gt;.[invoke](invoke.md)(): [T](invoke.md) |
