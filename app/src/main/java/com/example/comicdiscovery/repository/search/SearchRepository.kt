package com.example.comicdiscovery.repository.search

import com.example.comicdiscovery.repository.api.ApiClient
import com.example.comicdiscovery.repository.models.Character
import com.example.comicdiscovery.repository.models.Image
import com.example.comicdiscovery.repository.models.Result

class SearchRepository {

    suspend fun getSearch(query: String): Result<Character> {
        return ApiClient
            .getInterface()
            .getSearch(
                mapOf(
                    "query" to query,
                    "limit" to "10",
                    "sort" to "name:asc",
                    "resources" to "character",
                    "field_list" to "name,real_name,image"))
            .let { response ->
                Result(
                    response.limit,
                    response.results.map {
                        Character(
                            it.name,
                            it.realName,
                            Image(it.image.smallUrl)
                        )
                    }
                )
            }
    }
}