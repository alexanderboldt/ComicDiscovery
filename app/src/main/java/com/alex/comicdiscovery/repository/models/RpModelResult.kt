package com.alex.comicdiscovery.repository.models

sealed class RpModelResult<T> {
    data class Success<T>(val data: T) : RpModelResult<T>()
    data class Failure<T>(val throwable: Throwable) : RpModelResult<T>()
}