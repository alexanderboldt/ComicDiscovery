package com.alex.comicdiscovery.repository.search

import com.alex.comicdiscovery.repository.api.ApiClient
import com.alex.comicdiscovery.repository.models.RpModelCharacterOverview
import com.alex.comicdiscovery.repository.models.RpModelResponse
import com.alex.comicdiscovery.repository.models.RpModelResult
import com.alex.comicdiscovery.util.mapping.CharacterMapper

class SearchRepository {

    suspend fun getSearch(query: String): RpModelResult<RpModelResponse<List<RpModelCharacterOverview>>> {
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
                    RpModelResponse(
                        response.numberOfPageResults,
                        response.numberOfTotalResults,
                        CharacterMapper.mapApiModelOverviewToRpModelOverview(response.results)
                    )
                }.let { response -> RpModelResult.Success(response) }
        } catch (throwable: Throwable) {
            RpModelResult.Failure(throwable)
        }
    }
}