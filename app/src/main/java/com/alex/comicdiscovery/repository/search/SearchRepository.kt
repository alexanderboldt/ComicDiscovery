package com.alex.comicdiscovery.repository.search

import com.alex.comicdiscovery.repository.datasource.api.ApiRoutes
import com.alex.comicdiscovery.repository.models.RpModelCharacterOverview
import com.alex.comicdiscovery.repository.models.RpModelResponse
import com.alex.comicdiscovery.util.mapping.CharacterMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepository(private val apiRoutes: ApiRoutes) {

    suspend fun getSearch(query: String): Flow<RpModelResponse<List<RpModelCharacterOverview>>> {
        return flow {

            apiRoutes
                .getSearch(
                    mapOf(
                        "query" to query,
                        "limit" to "10",
                        "sort" to "name:asc",
                        "resources" to "character",
                        "field_list" to "id,name,real_name,image"
                    )
                ).let { response ->
                    RpModelResponse(
                        response.numberOfPageResults,
                        response.numberOfTotalResults,
                        CharacterMapper.mapApiModelOverviewToRpModelOverview(response.results)
                    )
                }.also { emit(it) }

        }.flowOn(Dispatchers.IO)
    }
}