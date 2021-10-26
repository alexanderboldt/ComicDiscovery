package com.alex.comicdiscovery.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import timber.log.Timber

/**
 * Extension which prints the throwable with Timber.
 *
 * @param action The function with the error-content.
 * @return Returns the actual flow.
 */
fun <T> Flow<T>.timberCatch(action: suspend FlowCollector<T>.(Throwable) -> Unit): Flow<T> {
    return catch { throwable ->
        Timber.w(throwable)
        action(throwable)
    }
}