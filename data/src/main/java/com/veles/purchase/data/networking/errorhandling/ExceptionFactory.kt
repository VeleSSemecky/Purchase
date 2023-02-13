package com.veles.purchase.data.networking.errorhandling

import com.veles.purchase.data.networking.entity.NetworkException
import com.veles.purchase.domain.model.exception.AnotherSpecificException
import com.veles.purchase.domain.model.exception.ConnectivityException
import com.veles.purchase.domain.model.exception.ReadableException
import com.veles.purchase.domain.model.exception.SomeExtraSpecificException
import com.veles.purchase.domain.model.exception.SomeSpecificException
import com.veles.purchase.domain.model.exception.SslException
import com.veles.purchase.domain.model.exception.UnauthorizedException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

class ExceptionFactory {

    fun create(throwable: Throwable): Throwable = when {
        throwable.isUnauthorizedException() -> UnauthorizedException(
            throwable.localizedMessage.orEmpty(),
            throwable.endpoint,
            throwable
        )
        throwable.isConnectivityException() -> ConnectivityException(
            throwable.localizedMessage.orEmpty(),
            throwable.endpoint,
            throwable
        )
        throwable.isSslException() -> SslException(
            throwable.localizedMessage.orEmpty(),
            throwable.endpoint,
            throwable
        )
        throwable.isSomeSpecificException() -> SomeSpecificException(
            throwable.localizedMessage.orEmpty(),
            throwable.endpoint,
            throwable
        )
        throwable.isSomeExtraSpecificException() -> SomeExtraSpecificException(
            throwable.localizedMessage.orEmpty(),
            throwable.endpoint,
            throwable
        )
        throwable.isAnotherSpecificException() -> AnotherSpecificException(
            throwable.localizedMessage.orEmpty(),
            throwable.endpoint,
            throwable
        )
        throwable is NetworkException -> ReadableException(
            throwable.localizedMessage.orEmpty(),
            throwable.endpoint,
            throwable
        )
        else -> throwable
    }

    private val Throwable.endpoint
        get() = (this as? NetworkException)?.endpoint ?: ""

    private fun Throwable.isUnauthorizedException() =
        this is NetworkException && type == NetworkException.Type.UNAUTHORIZED

    private fun Throwable.isSomeSpecificException() =
        this is NetworkException && type == NetworkException.Type.FORBIDDEN &&
            errorCode == ErrorCode.SOME_SPECIFIC_ERROR_CODE.value

    private fun Throwable.isSomeExtraSpecificException() =
        this is NetworkException && type == NetworkException.Type.FORBIDDEN &&
            errorCode == ErrorCode.SOME_EXTRA_SPECIFIC_ERROR_CODE.value

    private fun Throwable.isAnotherSpecificException() =
        this is NetworkException && type == NetworkException.Type.FORBIDDEN &&
            errorCode == ErrorCode.ANOTHER_SPECIFIC_ERROR_CODE.value

    private fun Throwable.isConnectivityException() =
        this is ConnectException ||
            this is UnknownHostException ||
            this is SocketTimeoutException ||
            this is SocketException

    private fun Throwable.isSslException() = this is SSLException

    enum class ErrorCode(val value: String) {
        SOME_SPECIFIC_ERROR_CODE("SomeSpecificErrorCode"),
        SOME_EXTRA_SPECIFIC_ERROR_CODE("SomeExtraSpecificErrorCode"),
        ANOTHER_SPECIFIC_ERROR_CODE("AnotherSpecificErrorCode")
    }
}
