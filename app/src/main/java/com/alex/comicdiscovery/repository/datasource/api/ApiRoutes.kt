package com.alex.comicdiscovery.repository.datasource.api

import com.alex.comicdiscovery.repository.datasource.api.models.ApiModelCharacterDetail
import com.alex.comicdiscovery.repository.datasource.api.models.ApiModelCharacterOverview
import com.alex.comicdiscovery.repository.datasource.api.models.ApiModelResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiRoutes {

    @GET("search")
    suspend fun getSearch(@QueryMap options: Map<String, String>): ApiModelResponse<List<ApiModelCharacterOverview>>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): ApiModelResponse<ApiModelCharacterDetail>
}