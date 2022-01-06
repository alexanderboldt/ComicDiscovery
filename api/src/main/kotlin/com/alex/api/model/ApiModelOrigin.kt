package com.alex.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelOrigin(val name: String)