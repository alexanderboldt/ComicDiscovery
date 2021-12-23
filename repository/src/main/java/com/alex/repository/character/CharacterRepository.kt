package com.alex.repository.character

import com.alex.api.ApiRoutes
import com.alex.repository.datasource.database.ComicDiscoveryDatabase
import com.alex.repository.mapping.toRpModel
import com.alex.repository.models.RpModelCharacter
import com.alex.repository.models.RpModelResponse
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
class CharacterRepository(private val apiRoutes: ApiRoutes, private val database: ComicDiscoveryDatabase) {

    private val idPrefix = "4005-"
    private val fields = "id,name,real_name,image,gender,aliases,birth,powers,origin"

    // assembles the actual id with a prefix
    private val Int.withPrefix: String
        get() = idPrefix + this

    // ----------------------------------------------------------------------------

    /**
     * Returns a character from the backend.
     *
     * @param id The Id as [Int].
     *
     * @return Returns [RpModelCharacter] in a [Flow].
     */
    suspend fun getCharacter(id: Int): Flow<RpModelResponse<RpModelCharacter>> {
        return flow {
            apiRoutes
                .getCharacter(id.withPrefix, fields)
                .let { response ->
                    RpModelResponse(
                        response.numberOfPageResults,
                        response.numberOfTotalResults,
                        response.results.toRpModel(database.characterDao().getCharacter(id) != null))
                }.also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    // ----------------------------------------------------------------------------

    /**
     * Returns a single character from the database.
     *
     * @param id The Id as [Int].
     *
     * @return Returns [RpModelCharacter] in a [Flow].
     */
    suspend fun getStarredCharacter(id: Int): Flow<RpModelResponse<RpModelCharacter>> {
        return flow {
            database
                .characterDao()
                .getCharacter(id)!!
                .let { character -> RpModelResponse(
                    1,
                    1,
                    character.toRpModel(true)) }
                .also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }
}