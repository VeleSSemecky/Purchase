package com.veles.purchase.domain.model.exception

class ConnectivityException(
    message: String,
    endpoint: String,
    cause: Throwable? = null
) : AppException(message, endpoint, cause)
