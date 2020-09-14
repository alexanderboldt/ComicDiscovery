package com.example.comicdiscovery.repository.api

import com.example.comicdiscovery.repository.api.models.GetSearchResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiRoutes {

    @GET("search")
    suspend fun getSearch(@QueryMap options: Map<String, String>): GetSearchResponse
}