package com.example.comicdiscovery.repository

import com.example.comicdiscovery.repository.api.ApiClient
import com.example.comicdiscovery.repository.models.Character
import com.example.comicdiscovery.repository.models.Result

class SearchRepository {

    suspend fun getSearch(query: String): Result<Character> {
        val apiResult = ApiClient.getInterface().get(query)

        return Result(
            apiResult.limit,
            apiResult.results.map {
                Character(
                    it.name
                )
            }
        )
    }
}