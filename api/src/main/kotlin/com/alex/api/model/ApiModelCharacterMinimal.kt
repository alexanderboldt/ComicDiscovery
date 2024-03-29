package com.alex.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelCharacterMinimal(
    val id: Int,
    val name: String,
    @Json(name = "real_name") val realName: String?,
    val image: ApiModelImage
)