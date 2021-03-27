package com.alex.comicdiscovery.repository.datasource.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelPower(val name: String)