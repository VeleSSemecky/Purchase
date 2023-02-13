package com.veles.purchase.domain.core.loger

interface Logger {

    fun e(tag: String?, msg: String?, throwable: Throwable)
    fun i(tag: String?, msg: String?)
    fun v(tag: String?, msg: String?)
    fun v(tag: String?, msg: String?, throwable: Throwable)
}
