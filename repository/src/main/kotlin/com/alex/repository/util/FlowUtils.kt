package com.alex.repository.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.experimental.ExperimentalTypeInference

/**
 * Convenient function to combine [Flow] with [Dispatchers.IO].
 *
 * @param block The function with the content.
 *
 * @return Returns a [Flow].
 */
@OptIn(ExperimentalTypeInference::class)
fun <T> flowIo(@BuilderInference block: suspend FlowCollector<T>.() -> Unit) = flow(block).flowOn(Dispatchers.IO)