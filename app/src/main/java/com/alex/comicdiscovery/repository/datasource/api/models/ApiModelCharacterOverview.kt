package com.alex.comicdiscovery.repository.datasource.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelCharacterOverview(
    val id: Int,
    val name: String,
    @Json(name = "real_name") val realName: String?,
    val image: ApiModelImage
)