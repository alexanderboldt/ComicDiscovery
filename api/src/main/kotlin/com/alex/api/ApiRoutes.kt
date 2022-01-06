package com.alex.api

import com.alex.api.model.ApiModelCharacter
import com.alex.api.model.ApiModelCharacterMinimal
import com.alex.api.model.ApiModelResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiRoutes {

    @GET("search")
    suspend fun getSearch(@QueryMap options: Map<String, String>): ApiModelResponse<List<ApiModelCharacterMinimal>>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String, @Query("field_list") fields: String): ApiModelResponse<ApiModelCharacter>
}