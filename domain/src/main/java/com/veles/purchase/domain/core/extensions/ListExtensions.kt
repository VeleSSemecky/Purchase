package com.veles.purchase.domain.core.extensions

fun <T : Any> List<T>.add(element: T): List<T> {
    val list = toMutableList()
    list.add(element)
    return list
}
