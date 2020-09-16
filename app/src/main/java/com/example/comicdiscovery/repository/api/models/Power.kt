package com.example.comicdiscovery.repository.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Power(val name: String)