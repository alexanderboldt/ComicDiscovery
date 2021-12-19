package com.alex.repository.models

data class RpModelResponse<T>(
    val numberOfPageResults: Int,
    val numberOfTotalResults: Int,
    val result: T
)