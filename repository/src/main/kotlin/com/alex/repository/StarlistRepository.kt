package com.alex.repository

import com.alex.api.ApiRoutes
import com.alex.database.ComicDiscoveryDatabase
import com.alex.database.starlist.DbModelStarlist
import com.alex.database.starlistCharacter.DbModelStarlistCharacter
import com.alex.repository.mapping.toDbModel
import com.alex.repository.mapping.toRpModel
import com.alex.repository.mapping.toRpModelMinimal
import com.alex.repository.model.RpModelCharacterMinimal
import com.alex.repository.model.RpModelList
import com.alex.repository.model.RpModelResponse
import com.alex.repository.util.fields
import com.alex.repository.util.flowIo
import com.alex.repository.util.withPrefix
import kotlinx.coroutines.flow.Flow

/**
 * Manages the handling of the Starlists and associated characters.
 *
 * @param apiRoutes An instance of [ApiRoutes].
 * @param database An instance of [ComicDiscoveryDatabase].
 */
class StarlistRepository(
    private val apiRoutes: ApiRoutes,
    private val database: ComicDiscoveryDatabase
) {

    // Starlist-functions

    /**
     * Creates a new Starlist and returns the new entry.
     *
     * @param name The name as [String] of the new Starlist.
     *
     * @return Returns [RpModelList] in a [Flow].
     */
    fun create(name: String) = flowIo {
        val id = database.starlistDao.insert(DbModelStarlist(0, name))
        emit(RpModelList(id, name))
    }

    /**
     * Returns all Starlists.
     *
     * @return Returns a [List] of [RpModelList] in a [Flow].
     */
    fun getAll() = flowIo {
        emit(database.starlistDao.getAll().toRpModel())
    }

    /**
     * Updates an existing Starlist with a new name.
     *
     * @param id The Id as [Long].
     * @param name The new name as [String].
     *
     * @return Returns true, if it was successful otherwise false.
     */
    fun update(id: Long, name: String) = flowIo {
        emit(database.starlistDao.update(DbModelStarlist(id, name)) > 0)
    }

    /**
     * Deletes a Starlist.
     *
     * @param id The Id as [Long].
     *
     * @return Returns true, if it was successful otherwise false in a [Flow].
     */
    fun delete(id: Long) = flowIo {
        emit(database.starlistDao.delete(id) > 0)
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
    fun linkCharacter(starlistId: Long, characterId: Int) = flowIo {
        if (database.characterDao.getCharacter(characterId) == null) {
            apiRoutes
                .getCharacter(characterId.withPrefix, fields)
                .results
                .let { character -> database.characterDao.insert(character.toDbModel()) }
        }

        database.starlistCharacterDao.insert(DbModelStarlistCharacter(starlistId, characterId))
        emit(true)
    }

    /**
     * Releases a character from a Starlist.
     *
     * @param starlistId The Id as [Long] of the Starlist.
     * @param characterId The Id as [Int] of the character.
     *
     * @return Returns true, if it was successful otherwise false in a [Flow].
     */
    fun releaseCharacter(starlistId: Long, characterId: Int) = flowIo {
        database.starlistCharacterDao.delete(starlistId, characterId)

        // if the current character is not associated with another list,
        // delete it from the characters-table
        if (database.starlistCharacterDao.getNumberOfAssociations(characterId) == 0) {
            database.characterDao.delete(characterId)
        }
        emit(true)
    }

    /**
     * Returns all characters associated to a Starlist.
     *
     * @param starlistId The Id as [Long].
     *
     * @return Returns a [List] of [RpModelCharacterMinimal] in a [Flow].
     */
    fun getStarredCharacters(starlistId: Long) = flowIo {
        database
            .starlistDao
            .getCharacters(starlistId)
            .let { characters ->
                RpModelResponse(
                    characters.size,
                    characters.size,
                    characters.toRpModelMinimal()
                )
            }.also { emit(it) }
    }

    /**
     * Returns all Starlists associated to a character.
     *
     * @param characterId The Id as [Int].
     *
     * @return Returns a [List] of [Long]-Id's in a [Flow].
     */
    fun getAssociatedStarlists(characterId: Int) = flowIo {
        emit(database.starlistCharacterDao.getAssociatedStarlists(characterId))
    }
}