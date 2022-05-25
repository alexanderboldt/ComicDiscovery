package com.alex.util

/**
 * Convenient function to clear a collection and to add other elements.
 */
fun <T> MutableCollection<T>.clearAndAdd(elements: Collection<T>) {
    clear()
    addAll(elements)
}