package com.alex.comicdiscovery.repository.datasource.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelResponse<T>(
    @Json(name = "number_of_page_results") val numberOfPageResults: Int,
    @Json(name = "number_of_total_results") val numberOfTotalResults: Int,
    val results: T)