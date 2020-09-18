package com.alex.comicdiscovery.repository.models

sealed class Result<R> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val throwable: Throwable) : Result<T>()
}