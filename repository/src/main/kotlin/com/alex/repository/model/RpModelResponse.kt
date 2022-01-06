package com.alex.repository.model

data class RpModelResponse<T>(
    val numberOfPageResults: Int,
    val numberOfTotalResults: Int,
    val result: T
)