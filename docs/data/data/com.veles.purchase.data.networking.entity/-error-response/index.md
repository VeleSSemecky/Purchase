//[data](../../../index.md)/[com.veles.purchase.data.networking.entity](../index.md)/[ErrorResponse](index.md)

# ErrorResponse

[androidJvm]\
data class [ErrorResponse](index.md)(val timestamp: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val status: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, val code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)

## Constructors

| | |
|---|---|
| [ErrorResponse](-error-response.md) | [androidJvm]<br>constructor(timestamp: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, status: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [code](code.md) | [androidJvm]<br>@SerializedName(value = &quot;error&quot;)<br>val [code](code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [endpoint](endpoint.md) | [androidJvm]<br>@SerializedName(value = &quot;path&quot;)<br>val [endpoint](endpoint.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [message](message.md) | [androidJvm]<br>@SerializedName(value = &quot;message&quot;)<br>val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [status](status.md) | [androidJvm]<br>@SerializedName(value = &quot;status&quot;)<br>val [status](status.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? |
| [timestamp](timestamp.md) | [androidJvm]<br>@SerializedName(value = &quot;timestamp&quot;)<br>val [timestamp](timestamp.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
