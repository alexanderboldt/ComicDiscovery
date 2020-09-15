package com.example.comicdiscovery.repository.models

data class Response<T>(
    val limit: Int,
    val result: List<T>
)