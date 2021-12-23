package com.alex.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelOrigin(val name: String)