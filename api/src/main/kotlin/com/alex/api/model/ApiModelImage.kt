package com.alex.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelImage(
    @Json(name = "small_url") val smallUrl: String
)