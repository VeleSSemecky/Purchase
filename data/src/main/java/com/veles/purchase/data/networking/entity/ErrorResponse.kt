package com.veles.purchase.data.networking.entity

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @SerializedName("timestamp")
    val timestamp: String?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("error")
    val code: String?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("path")
    val endpoint: String?
) {

    companion object {

        val EMPTY = ErrorResponse(
            timestamp = "",
            status = 0,
            code = "",
            message = "Unknown error",
            endpoint = ""
        )
    }
}
