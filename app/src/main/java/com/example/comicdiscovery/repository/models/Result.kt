package com.example.comicdiscovery.repository.models

data class Result<T>(
    val limit: Int,
    val result: List<T>
)