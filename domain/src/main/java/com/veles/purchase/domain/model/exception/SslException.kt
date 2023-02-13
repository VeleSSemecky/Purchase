package com.veles.purchase.domain.model.exception

class SslException(
    message: String,
    endpoint: String,
    cause: Throwable? = null
) : AppException(message, endpoint, cause)
