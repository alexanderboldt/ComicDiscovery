package com.alex.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelPower(val name: String)