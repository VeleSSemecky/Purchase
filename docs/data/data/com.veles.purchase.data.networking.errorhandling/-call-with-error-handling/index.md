//[data](../../../index.md)/[com.veles.purchase.data.networking.errorhandling](../index.md)/[CallWithErrorHandling](index.md)

# CallWithErrorHandling

[androidJvm]\
class [CallWithErrorHandling](index.md)(delegate: Call&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, exceptionFactory: [ExceptionFactory](../-exception-factory/index.md), gson: Gson) : Call&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;

## Constructors

| | |
|---|---|
| [CallWithErrorHandling](-call-with-error-handling.md) | [androidJvm]<br>constructor(delegate: Call&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, exceptionFactory: [ExceptionFactory](../-exception-factory/index.md), gson: Gson) |

## Functions

| Name | Summary |
|---|---|
| [cancel](index.md#-228897502%2FFunctions%2F-70787932) | [androidJvm]<br>open override fun [cancel](index.md#-228897502%2FFunctions%2F-70787932)() |
| [clone](clone.md) | [androidJvm]<br>open override fun [clone](clone.md)(): [CallWithErrorHandling](index.md) |
| [enqueue](enqueue.md) | [androidJvm]<br>open override fun [enqueue](enqueue.md)(callback: Callback&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;) |
| [execute](index.md#1599628073%2FFunctions%2F-70787932) | [androidJvm]<br>open override fun [execute](index.md#1599628073%2FFunctions%2F-70787932)(): Response&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; |
| [isCanceled](index.md#-1101881255%2FFunctions%2F-70787932) | [androidJvm]<br>open override fun [isCanceled](index.md#-1101881255%2FFunctions%2F-70787932)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isExecuted](index.md#1437552163%2FFunctions%2F-70787932) | [androidJvm]<br>open override fun [isExecuted](index.md#1437552163%2FFunctions%2F-70787932)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [request](index.md#-2120926897%2FFunctions%2F-70787932) | [androidJvm]<br>open override fun [request](index.md#-2120926897%2FFunctions%2F-70787932)(): Request |
| [timeout](index.md#682905277%2FFunctions%2F-70787932) | [androidJvm]<br>open override fun [timeout](index.md#682905277%2FFunctions%2F-70787932)(): Timeout |
