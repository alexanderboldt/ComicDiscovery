package com.alex.repository

import com.alex.api.ApiRoutes
import com.alex.database.ComicDiscoveryDatabase
import com.alex.repository.mapping.toRpModel
import com.alex.repository.model.RpModelCharacter
import com.alex.repository.model.RpModelResponse
import com.alex.repository.util.fields
import com.alex.repository.util.withPrefix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Manages the data-handling of the characters.
 *
 * @param apiRoutes An instance of [ApiRoutes].
 * @param database An instance of [ComicDiscoveryDatabase].
 */
class CharacterRepository(
    private val apiRoutes: ApiRoutes,
    private val database: ComicDiscoveryDatabase
) {

    /**
     * Returns a character from the backend.
     *
     * @param id The Id as [Int].
     *
     * @return Returns [RpModelCharacter] in a [Flow].
     */
    suspend fun getCharacter(id: Int) = flow {
        apiRoutes
            .getCharacter(id.withPrefix, fields)
            .let { response ->
                RpModelResponse(
                    response.numberOfPageResults,
                    response.numberOfTotalResults,
                    response.results.toRpModel(database.characterDao.getCharacter(id) != null)
                )
            }.also { emit(it) }
    }.flowOn(Dispatchers.IO)

    // ----------------------------------------------------------------------------

    /**
     * Returns a single character from the database.
     *
     * @param id The Id as [Int].
     *
     * @return Returns [RpModelCharacter] in a [Flow].
     */
    suspend fun getStarredCharacter(id: Int) = flow {
        database
            .characterDao
            .getCharacter(id)!!
            .let { character ->
                RpModelResponse(
                    1,
                    1,
                    character.toRpModel(true)
                )
            }.also { emit(it) }
    }.flowOn(Dispatchers.IO)
}