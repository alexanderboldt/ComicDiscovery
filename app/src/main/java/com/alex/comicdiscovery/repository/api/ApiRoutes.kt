package com.alex.comicdiscovery.repository.api

import com.alex.comicdiscovery.repository.api.models.CharacterDetail
import com.alex.comicdiscovery.repository.api.models.CharacterOverview
import com.alex.comicdiscovery.repository.api.models.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiRoutes {

    @GET("search")
    suspend fun getSearch(@QueryMap options: Map<String, String>): Response<List<CharacterOverview>>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String, @QueryMap options: Map<String, String>): Response<CharacterDetail>
}