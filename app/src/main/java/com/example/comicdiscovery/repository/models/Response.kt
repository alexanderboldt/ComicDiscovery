package com.example.comicdiscovery.repository.models

data class Response<T>(
    val numberOfPageResults: Int,
    val numberOfTotalResults: Int,
    val result: T
)