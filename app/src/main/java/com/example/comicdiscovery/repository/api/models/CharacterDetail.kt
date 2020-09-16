package com.example.comicdiscovery.repository.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDetail(
    val name: String,
    @Json(name = "real_name") val realName: String?,
    val image: Image,
    val gender: Int,
    val aliases: String,
    val birth: String,
    val powers: List<Power>,
    val origin: Origin
)