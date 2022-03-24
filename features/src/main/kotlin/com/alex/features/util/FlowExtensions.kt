package com.alex.features.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import logcat.asLog
import logcat.logcat

/**
 * Extension function which prints the throwable.
 *
 * @param action The function with the error-content.
 * @return Returns the actual flow.
 */
fun <T> Flow<T>.printCatch(action: suspend FlowCollector<T>.(Throwable) -> Unit) = catch { throwable ->
    logcat { throwable.asLog() }
    action(throwable)
}