package com.veles.purchase.domain.model.exception

abstract class AppException(
    private val readableMessage: String = "",
    val endpoint: String = "",
    override val cause: Throwable? = null
) : RuntimeException() {

    override val message: String
        get() = readableMessage.takeIf { it.isNotEmpty() } ?: "Empty message"
}
