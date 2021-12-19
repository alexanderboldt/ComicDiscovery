package com.alex.repository.datasource.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelPower(val name: String)