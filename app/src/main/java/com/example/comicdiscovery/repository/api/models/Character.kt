package com.example.comicdiscovery.repository.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    val name: String,
    @Json(name = "real_name") val realName: String?,
    val image: Image
)