package com.veles.purchase.data.networking.entity

import com.google.gson.Gson
import kotlin.properties.Delegates
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class NetworkException(
    private val gson: Gson,
    httpException: HttpException
) : Throwable(httpException) {

    private var raw: JSONObject by Delegates.notNull()
    private val errorResponse: ErrorResponse by lazy { parseExceptionBody(httpException) }
    val type: Type by lazy { parseExceptionType(httpException) }

    override val message: String = errorResponse.message ?: ""

    val errorCode: String = errorResponse.code ?: ""

    val endpoint: String = errorResponse.endpoint ?: ""

    private fun parseExceptionBody(httpException: HttpException): ErrorResponse {
        val errorBody = httpException.response()?.errorBody()?.string()
        return try {
            raw = JSONObject(errorBody ?: "")
            val errorResponseResult: ErrorResponse? =
                gson.fromJson(errorBody, ErrorResponse::class.java)
            errorResponseResult ?: createUnknownNetworkError()
        } catch (ex: JSONException) {
            createUnknownNetworkError()
        }
    }

    private fun createUnknownNetworkError() = ErrorResponse.EMPTY

    @Suppress("MagicNumber")
    private fun parseExceptionType(throwable: Throwable): Type =
        when ((throwable as HttpException).code()) {
            401 -> Type.UNAUTHORIZED
            403 -> Type.FORBIDDEN
            404 -> Type.NOT_FOUND
            422 -> Type.UNPROCESSABLE_ENTITY
            426 -> Type.UPGRADE_REQUIRED
            500 -> Type.SERVER_ERROR
            502 -> Type.BAD_GATEWAY
            else -> Type.CUSTOM
        }

    enum class Type {
        BAD_GATEWAY,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        SERVER_ERROR,
        UPGRADE_REQUIRED,
        UNPROCESSABLE_ENTITY,
        CUSTOM
    }

    companion object {

        fun wrapThrowableOrCause(gson: Gson, throwable: Throwable) =
            (throwable as? HttpException)?.let { NetworkException(gson, it) } ?: throwable
    }
}
