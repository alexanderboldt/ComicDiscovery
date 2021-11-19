package com.alex.comicdiscovery.repository.character

import com.alex.comicdiscovery.repository.datasource.api.ApiRoutes
import com.alex.comicdiscovery.repository.datasource.database.ComicDiscoveryDatabase
import com.alex.comicdiscovery.repository.models.*
import com.alex.comicdiscovery.util.mapping.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Manages the data-handling of the characters.
 *
 * @param apiRoutes The ApiRoutes will be automatically injected.
 * @param database The ComicDiscoveryDatabase will be automatically injected.
 */
class CharacterRepository(private val apiRoutes: ApiRoutes, private val database: ComicDiscoveryDatabase) {

    private val idPrefix = "4005-"
    private val fields = "id,name,real_name,image,gender,aliases,birth,powers,origin"

    // ----------------------------------------------------------------------------

    /**
     * Gets a character from the backend.
     *
     * @param id The id of the character.
     * @return Returns a flow with the response.
     */
    suspend fun getCharacterDetail(id: Int): Flow<RpModelResponse<RpModelCharacterDetail>> {
        return flow {
            apiRoutes
                .getCharacter(getId(id), fields)
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
     * Gets a single character from the database.
     *
     * @param id The id of the character.
     * @return Returns a flow with the character.
     */
    suspend fun getStarredCharacter(id: Int): Flow<RpModelResponse<RpModelCharacterDetail>> {
        return flow {
            database
                .characterDao()
                .getCharacter(id)!!
                .let { character -> RpModelResponse(
                    1,
                    1,
                    character.toRpModelDetail(true)) }
                .also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    // ----------------------------------------------------------------------------

    /**
     * Assembles the actual id with a prefix.
     *
     * @param id The id of the character.
     * @return Returns the complete id.
     */
    private fun getId(id: Int) = idPrefix + id
}