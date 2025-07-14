package com.veles.purchase.presentation.data.loger

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.veles.purchase.domain.core.loger.Logger

class LoggerImpl(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : Logger {

    override fun e(tag: String?, msg: String?, throwable: Throwable) {
        Log.e(tag, msg, throwable)
        firebaseCrashlytics.recordException(throwable)
    }

    override fun i(tag: String?, msg: String?) {
        Log.i(tag, msg ?: "")
    }

    override fun v(tag: String?, msg: String?) {
        Log.v(tag, msg ?: "")
    }

    override fun v(tag: String?, msg: String?, throwable: Throwable) {
        Log.v(tag, msg ?: "", throwable)
        firebaseCrashlytics.recordException(throwable)
    }
}
