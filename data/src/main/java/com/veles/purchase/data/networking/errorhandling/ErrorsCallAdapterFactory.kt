package com.veles.purchase.data.networking.errorhandling

import com.google.gson.Gson
import com.veles.purchase.data.networking.entity.core.NetworkException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit

class ErrorsCallAdapterFactory(
    private val exceptionFactory: ExceptionFactory,
    private val gson: Gson
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, Call<*>>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType || returnType.actualTypeArguments.size != 1) {
            return null
        }

        val delegate = retrofit.nextCallAdapter(this, returnType, annotations)
        @Suppress("UNCHECKED_CAST")
        return ErrorsCallAdapter(
            delegateAdapter = delegate as CallAdapter<Any, Call<*>>,
            exceptionFactory = exceptionFactory,
            gson = gson
        )
    }
}

class ErrorsCallAdapter(
    private val exceptionFactory: ExceptionFactory,
    private val gson: Gson,
    private val delegateAdapter: CallAdapter<Any, Call<*>>
) : CallAdapter<Any, Call<*>> by delegateAdapter {

    override fun adapt(call: Call<Any>): Call<*> =
        delegateAdapter.adapt(CallWithErrorHandling(call, exceptionFactory, gson))
}

class CallWithErrorHandling(
    private val delegate: Call<Any>,
    private val exceptionFactory: ExceptionFactory,
    private val gson: Gson
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(call, convertException(HttpException(response)))
                }
            }

            override fun onFailure(call: Call<Any>, throwable: Throwable) {
                callback.onFailure(call, convertException(throwable))
            }
        })
    }

    override fun clone() = CallWithErrorHandling(delegate.clone(), exceptionFactory, gson)

    private fun convertException(throwable: Throwable) =
        exceptionFactory.create(NetworkException.wrapThrowableOrCause(gson, throwable))
}
