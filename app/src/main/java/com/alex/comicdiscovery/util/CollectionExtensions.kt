package com.alex.comicdiscovery.util

fun <T> MutableCollection<T>.clearAndAdd(elements: Collection<T>) {
    clear()
    addAll(elements)
}