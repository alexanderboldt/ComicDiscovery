package com.example.comicdiscovery.repository.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetSearchResponse(
    val limit: Int,
    val results: List<Character>
)