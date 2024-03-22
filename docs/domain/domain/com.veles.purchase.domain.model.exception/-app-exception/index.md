//[domain](../../../index.md)/[com.veles.purchase.domain.model.exception](../index.md)/[AppException](index.md)

# AppException

abstract class [AppException](index.md)(readableMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val cause: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null) : [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html)

#### Inheritors

| |
|---|
| [AnotherSpecificException](../-another-specific-exception/index.md) |
| [ConnectivityException](../-connectivity-exception/index.md) |
| [ReadableException](../-readable-exception/index.md) |
| [SomeExtraSpecificException](../-some-extra-specific-exception/index.md) |
| [SomeSpecificException](../-some-specific-exception/index.md) |
| [SslException](../-ssl-exception/index.md) |
| [UnauthorizedException](../-unauthorized-exception/index.md) |

## Constructors

| | |
|---|---|
| [AppException](-app-exception.md) | [jvm]<br>constructor(readableMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, cause: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [cause](cause.md) | [jvm]<br>open override val [cause](cause.md): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null |
| [endpoint](endpoint.md) | [jvm]<br>val [endpoint](endpoint.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [message](message.md) | [jvm]<br>open override val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

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
