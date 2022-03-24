package com.alex.repository

import com.alex.api.ApiRoutes
import com.alex.repository.mapping.toRpModel
import com.alex.repository.model.RpModelCharacterMinimal
import com.alex.repository.model.RpModelResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Manages the data-handling of search-operations.
 *
 * @param apiRoutes An instance of [ApiRoutes].
 */
class SearchRepository(private val apiRoutes: ApiRoutes) {

    /**
     * Makes a search for characters from the backend.
     *
     * @param query The search-query as [String].
     * @param limit The limit as [Int].
     * @return Returns a [List] of [RpModelCharacterMinimal] in a [Flow].
     */
    suspend fun getSearch(query: String, limit: Int) = flow {
        apiRoutes
            .getSearch(
                mapOf(
                    "query" to query,
                    "limit" to limit.toString(),
                    "sort" to "name:asc",
                    "resources" to "character",
                    "field_list" to "id,name,real_name,image"
                )
            ).let { response ->
                RpModelResponse(
                    response.numberOfPageResults,
                    response.numberOfTotalResults,
                    response.results.toRpModel()
                )
            }.also { emit(it) }
    }.flowOn(Dispatchers.IO)
}