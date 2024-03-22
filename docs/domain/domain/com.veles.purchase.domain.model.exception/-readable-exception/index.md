//[domain](../../../index.md)/[com.veles.purchase.domain.model.exception](../index.md)/[ReadableException](index.md)

# ReadableException

[jvm]\
class [ReadableException](index.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val cause: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null) : [AppException](../-app-exception/index.md)

## Constructors

| | |
|---|---|
| [ReadableException](-readable-exception.md) | [jvm]<br>constructor(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), cause: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [cause](../-app-exception/cause.md) | [jvm]<br>open override val [cause](../-app-exception/cause.md): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null |
| [endpoint](../-app-exception/endpoint.md) | [jvm]<br>val [endpoint](../-app-exception/endpoint.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [message](../-app-exception/message.md) | [jvm]<br>open override val [message](../-app-exception/message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [addSuppressed](../-unauthorized-exception/index.md#282858770%2FFunctions%2F-1078502285) | [jvm]<br>fun [addSuppressed](../-unauthorized-exception/index.md#282858770%2FFunctions%2F-1078502285)(p0: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)) |
| [fillInStackTrace](../-unauthorized-exception/index.md#-1102069925%2FFunctions%2F-1078502285) | [jvm]<br>open fun [fillInStackTrace](../-unauthorized-exception/index.md#-1102069925%2FFunctions%2F-1078502285)(): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) |
| [getLocalizedMessage](../-unauthorized-exception/index.md#1043865560%2FFunctions%2F-1078502285) | [jvm]<br>open fun [getLocalizedMessage](../-unauthorized-exception/index.md#1043865560%2FFunctions%2F-1078502285)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getStackTrace](../-unauthorized-exception/index.md#2050903719%2FFunctions%2F-1078502285) | [jvm]<br>open fun [getStackTrace](../-unauthorized-exception/index.md#2050903719%2FFunctions%2F-1078502285)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[StackTraceElement](https://docs.oracle.com/javase/8/docs/api/java/lang/StackTraceElement.html)&gt; |
| [getSuppressed](../-unauthorized-exception/index.md#672492560%2FFunctions%2F-1078502285) | [jvm]<br>fun [getSuppressed](../-unauthorized-exception/index.md#672492560%2FFunctions%2F-1078502285)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)&gt; |
| [initCause](../-unauthorized-exception/index.md#-418225042%2FFunctions%2F-1078502285) | [jvm]<br>open fun [initCause](../-unauthorized-exception/index.md#-418225042%2FFunctions%2F-1078502285)(p0: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) |
| [printStackTrace](../-unauthorized-exception/index.md#-1769529168%2FFunctions%2F-1078502285) | [jvm]<br>open fun [printStackTrace](../-unauthorized-exception/index.md#-1769529168%2FFunctions%2F-1078502285)()<br>open fun [printStackTrace](../-unauthorized-exception/index.md#1841853697%2FFunctions%2F-1078502285)(p0: [PrintStream](https://docs.oracle.com/javase/8/docs/api/java/io/PrintStream.html))<br>open fun [printStackTrace](../-unauthorized-exception/index.md#1175535278%2FFunctions%2F-1078502285)(p0: [PrintWriter](https://docs.oracle.com/javase/8/docs/api/java/io/PrintWriter.html)) |
| [setStackTrace](../-unauthorized-exception/index.md#2135801318%2FFunctions%2F-1078502285) | [jvm]<br>open fun [setStackTrace](../-unauthorized-exception/index.md#2135801318%2FFunctions%2F-1078502285)(p0: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[StackTraceElement](https://docs.oracle.com/javase/8/docs/api/java/lang/StackTraceElement.html)&gt;) |
