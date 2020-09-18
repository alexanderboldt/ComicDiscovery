package com.alex.comicdiscovery.repository.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterOverview(
    val id: Int,
    val name: String,
    @Json(name = "real_name") val realName: String?,
    val image: Image
)