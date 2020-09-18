package com.alex.comicdiscovery.repository.search

import com.alex.comicdiscovery.repository.api.ApiClient
import com.alex.comicdiscovery.repository.models.CharacterOverview
import com.alex.comicdiscovery.repository.models.Image
import com.alex.comicdiscovery.repository.models.Response
import com.alex.comicdiscovery.repository.models.Result

class SearchRepository {

    suspend fun getSearch(query: String): Result<Response<List<CharacterOverview>>> {
        return try {
            ApiClient
                .getInterface()
                .getSearch(
                    mapOf(
                        "query" to query,
                        "limit" to "10",
                        "sort" to "name:asc",
                        "resources" to "character",
                        "field_list" to "id,name,real_name,image"))
                .let { response ->
                    Response(
                        response.numberOfPageResults,
                        response.numberOfTotalResults,
                        response.results.map {
                            CharacterOverview(
                                it.id,
                                it.name,
                                it.realName,
                                Image(it.image.smallUrl)
                            )
                        }
                    )
                }.let { response -> Result.Success(response) }
        } catch (throwable: Throwable) {
            Result.Failure(throwable)
        }
    }
}