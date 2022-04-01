package com.alex.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelEnemy(
    val name: String
)
