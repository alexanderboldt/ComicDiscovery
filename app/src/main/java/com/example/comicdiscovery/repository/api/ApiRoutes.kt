package com.example.comicdiscovery.repository.api

import com.example.comicdiscovery.repository.api.models.GetSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRoutes {

    @GET("search?&sort=name:asc&limit=10&resources=character")
    suspend fun get(
        @Query("query") query: String
    ): GetSearchResponse
}