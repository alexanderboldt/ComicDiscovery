package com.alex.repository.starlist

import com.alex.api.ApiRoutes
import com.alex.repository.datasource.database.ComicDiscoveryDatabase
import com.alex.repository.datasource.database.starlist.DbModelStarlist
import com.alex.repository.datasource.database.starlistCharacter.DbModelStarlistCharacter
import com.alex.repository.mapping.toDbModel
import com.alex.repository.mapping.toRpModel
import com.alex.repository.mapping.toRpModelMinimal
import com.alex.repository.models.RpModelCharacterMinimal
import com.alex.repository.models.RpModelList
import com.alex.repository.models.RpModelResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Manages the handling of the Starlists and associated characters.
 *
 * @param apiRoutes An instance of [ApiRoutes].
 * @param database An instance of [ComicDiscoveryDatabase].
 */
class StarlistRepository(private val apiRoutes: ApiRoutes, private val database: ComicDiscoveryDatabase) {

    private val idPrefix = "4005-"
    private val fields = "id,name,real_name,image,gender,aliases,birth,powers,origin"

    // assembles the actual id with a prefix
    private val Int.withPrefix: String
        get() = idPrefix + this

    // ----------------------------------------------------------------------------
    // Starlist-functions

    /**
     * Creates a new Starlist and returns the new entry.
     *
     * @param name The name as [String] of the new Starlist.
     *
     * @return Returns [RpModelList] in a [Flow].
     */
    fun create(name: String): Flow<RpModelList> {
        return flow {
            val id = database.starlistDao().insert(DbModelStarlist(0, name))
            emit(RpModelList(id, name))
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Returns all Starlists.
     *
     * @return Returns a [List] of [RpModelList] in a [Flow].
     */
    fun getAll(): Flow<List<RpModelList>> {
        return flow {
            emit(database.starlistDao().getAll().toRpModel())
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Updates an existing Starlist with a new name.
     *
     * @param id The Id as [Long].
     * @param name The new name as [String].
     *
     * @return Returns true, if it was successful otherwise false.
     */
    fun update(id: Long, name: String): Flow<Boolean> {
        return flow {
            val affectedRows = database.starlistDao().update(DbModelStarlist(id, name))
            emit(affectedRows > 0)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Deletes a Starlist.
     *
     * @param id The Id as [Long].
     *
     * @return Returns true, if it was successful otherwise false in a [Flow].
     */
    fun delete(id: Long): Flow<Boolean> {
        return flow {
            val affectedRows = database.starlistDao().delete(id)
            emit(affectedRows > 0)
        }.flowOn(Dispatchers.IO)
    }

    // ----------------------------------------------------------------------------
    // Associated characters

    /**
     * Creates a link between a Starlist and a character.
     *
     * @param starlistId The Id as [Long] of the Starlist.
     * @param characterId The Id as [Int] of the character.
     *
     * @return Returns true, if it was successful otherwise false in a [Flow].
     */
    fun linkCharacter(starlistId: Long, characterId: Int): Flow<Boolean> {
        return flow {
            if (database.characterDao().getCharacter(characterId) == null) {
                apiRoutes
                    .getCharacter(characterId.withPrefix, fields)
                    .results
                    .let { character -> database.characterDao().insert(character.toDbModel()) }
            }

            database.starlistCharacterDao().insert(DbModelStarlistCharacter(starlistId, characterId))
            emit(true)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Releases a character from a Starlist.
     *
     * @param starlistId The Id as [Long] of the Starlist.
     * @param characterId The Id as [Int] of the character.
     *
     * @return Returns true, if it was successful otherwise false in a [Flow].
     */
    fun releaseCharacter(starlistId: Long, characterId: Int): Flow<Boolean> {
        return flow {
            database.starlistCharacterDao().delete(starlistId, characterId)

            // if the current character is not associated with another list,
            // delete it from the characters-table
            if (database.starlistCharacterDao().getNumberOfAssociations(characterId) == 0) {
                database.characterDao().delete(characterId)
            }
            emit(true)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Returns all characters associated to a Starlist.
     *
     * @param starlistId The Id as [Long].
     *
     * @return Returns a [List] of [RpModelCharacterMinimal] in a [Flow].
     */
    fun getStarredCharacters(starlistId: Long): Flow<RpModelResponse<List<RpModelCharacterMinimal>>> {
        return flow {
            database
                .starlistDao()
                .getCharacters(starlistId)
                .let { characters ->
                    RpModelResponse(
                        characters.size,
                        characters.size,
                        characters.toRpModelMinimal())
                }.also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Returns all Starlists associated to a character.
     *
     * @param characterId The Id as [Int].
     *
     * @return Returns a [List] of [Long]-Id's in a [Flow].
     */
    fun getAssociatedStarlists(characterId: Int): Flow<List<Long>> {
        return flow {
            emit(database.starlistCharacterDao().getAssociatedStarlists(characterId))
        }.flowOn(Dispatchers.IO)
    }
}