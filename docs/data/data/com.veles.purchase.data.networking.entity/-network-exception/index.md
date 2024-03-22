//[data](../../../index.md)/[com.veles.purchase.data.networking.entity](../index.md)/[NetworkException](index.md)

# NetworkException

[androidJvm]\
class [NetworkException](index.md)(gson: Gson, httpException: HttpException) : [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)

## Constructors

| | |
|---|---|
| [NetworkException](-network-exception.md) | [androidJvm]<br>constructor(gson: Gson, httpException: HttpException) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |
| [Type](-type/index.md) | [androidJvm]<br>enum [Type](-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[NetworkException.Type](-type/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [cause](index.md#-654012527%2FProperties%2F-70787932) | [androidJvm]<br>open val [cause](index.md#-654012527%2FProperties%2F-70787932): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? |
| [endpoint](endpoint.md) | [androidJvm]<br>val [endpoint](endpoint.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [errorCode](error-code.md) | [androidJvm]<br>val [errorCode](error-code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [message](message.md) | [androidJvm]<br>open override val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [type](type.md) | [androidJvm]<br>val [type](type.md): [NetworkException.Type](-type/index.md) |

## Functions

| Name | Summary |
|---|---|
| [addSuppressed](index.md#282858770%2FFunctions%2F-70787932) | [androidJvm]<br>fun [addSuppressed](index.md#282858770%2FFunctions%2F-70787932)(p0: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)) |
| [fillInStackTrace](index.md#-1102069925%2FFunctions%2F-70787932) | [androidJvm]<br>open fun [fillInStackTrace](index.md#-1102069925%2FFunctions%2F-70787932)(): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) |
| [getLocalizedMessage](index.md#1043865560%2FFunctions%2F-70787932) | [androidJvm]<br>open fun [getLocalizedMessage](index.md#1043865560%2FFunctions%2F-70787932)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getStackTrace](index.md#2050903719%2FFunctions%2F-70787932) | [androidJvm]<br>open fun [getStackTrace](index.md#2050903719%2FFunctions%2F-70787932)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[StackTraceElement](https://developer.android.com/reference/kotlin/java/lang/StackTraceElement.html)&gt; |
| [getSuppressed](index.md#672492560%2FFunctions%2F-70787932) | [androidJvm]<br>fun [getSuppressed](index.md#672492560%2FFunctions%2F-70787932)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)&gt; |
| [initCause](index.md#-418225042%2FFunctions%2F-70787932) | [androidJvm]<br>open fun [initCause](index.md#-418225042%2FFunctions%2F-70787932)(p0: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) |
| [printStackTrace](index.md#-1769529168%2FFunctions%2F-70787932) | [androidJvm]<br>open fun [printStackTrace](index.md#-1769529168%2FFunctions%2F-70787932)()<br>open fun [printStackTrace](index.md#1841853697%2FFunctions%2F-70787932)(p0: [PrintStream](https://developer.android.com/reference/kotlin/java/io/PrintStream.html))<br>open fun [printStackTrace](index.md#1175535278%2FFunctions%2F-70787932)(p0: [PrintWriter](https://developer.android.com/reference/kotlin/java/io/PrintWriter.html)) |
| [setStackTrace](index.md#2135801318%2FFunctions%2F-70787932) | [androidJvm]<br>open fun [setStackTrace](index.md#2135801318%2FFunctions%2F-70787932)(p0: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[StackTraceElement](https://developer.android.com/reference/kotlin/java/lang/StackTraceElement.html)&gt;) |
