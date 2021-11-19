package com.alex.comicdiscovery.repository.starlist

import com.alex.comicdiscovery.repository.datasource.api.ApiRoutes
import com.alex.comicdiscovery.repository.datasource.database.ComicDiscoveryDatabase
import com.alex.comicdiscovery.repository.datasource.database.starlist.DbModelStarlist
import com.alex.comicdiscovery.repository.datasource.database.starlistCharacter.DbModelStarlistCharacter
import com.alex.comicdiscovery.repository.models.RpModelCharacterOverview
import com.alex.comicdiscovery.repository.models.RpModelList
import com.alex.comicdiscovery.repository.models.RpModelResponse
import com.alex.comicdiscovery.util.mapping.toDbModel
import com.alex.comicdiscovery.util.mapping.toRpModel
import com.alex.comicdiscovery.util.mapping.toRpModelOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StarlistRepository(
    private val apiRoutes: ApiRoutes,
    private val database: ComicDiscoveryDatabase) {

    private val idPrefix = "4005-"
    private val fields = "id,name,real_name,image,gender,aliases,birth,powers,origin"

    // ----------------------------------------------------------------------------

    fun createStarlist(name: String): Flow<Unit> {
        return flow {
            database.starlistDao().insert(DbModelStarlist(0, name))
            emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

    fun addCharacter(starlistId: Long, characterId: Int): Flow<Unit> {
        return flow {
            apiRoutes
                .getCharacter(getId(characterId), fields)
                .results
                .let { character -> database.characterDao().insert(character.toDbModel()) }

            database.starlistCharacterDao().insert(DbModelStarlistCharacter(starlistId, characterId))
            emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

    fun removeCharacter(starlistId: Long, characterId: Int): Flow<Unit> {
        return flow {
            database.starlistCharacterDao().delete(starlistId, characterId)

            // if the current character is not associated with another list,
            // delete it from the characters-table
            if (database.starlistCharacterDao().getNumberOfAssociations(characterId) == 0) {
                database.characterDao().delete(characterId)
            }
            emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

    // ----------------------------------------------------------------------------

    fun getAllStarlists(): Flow<List<RpModelList>> {
        return flow {
            emit(database.starlistDao().getAll().toRpModel())
        }.flowOn(Dispatchers.IO)
    }

    fun getStarredCharactersOverview(starlistId: Long): Flow<RpModelResponse<List<RpModelCharacterOverview>>> {
        return flow {
            database
                .starlistDao()
                .getCharacters(starlistId)
                .let { characters ->
                    RpModelResponse(
                        characters.size,
                        characters.size,
                        characters.toRpModelOverview())
                }.also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    // ----------------------------------------------------------------------------

    fun updateStarlist(id: Long, name: String): Flow<Unit> {
        return flow {
            database.starlistDao().update(DbModelStarlist(id, name))
            emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

    // ----------------------------------------------------------------------------

    fun deleteStarlist(id: Long): Flow<Unit> {
        return flow {
            database.starlistDao().delete(id)
            emit(Unit)
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