package com.alex.comicdiscovery.repository.character

import com.alex.comicdiscovery.repository.datasource.api.ApiRoutes
import com.alex.comicdiscovery.repository.datasource.database.ComicDiscoveryDatabase
import com.alex.comicdiscovery.repository.models.*
import com.alex.comicdiscovery.util.mapping.CharacterMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepository(private val apiRoutes: ApiRoutes, private val database: ComicDiscoveryDatabase) {

    suspend fun getCharacter(id: Int): Flow<RpModelResponse<RpModelCharacterDetail>> {
        return flow {
            apiRoutes
                .getCharacter(
                    "4005-$id",
                    mapOf("field_list" to "id,name,real_name,image,aliases,birth,gender,powers,origin")
                )
                .let { response ->
                    RpModelResponse(
                        response.numberOfPageResults,
                        response.numberOfTotalResults,
                        CharacterMapper.mapApiModelDetailToRpModelDetail(
                            response.results,
                            database.characterDao()
                                .getCharacter(id) != null
                        )
                    )
                }.also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    // ----------------------------------------------------------------------------

    suspend fun getStarredCharacters(): Flow<RpModelResponse<List<RpModelCharacterOverview>>> {
        return flow {
            database
                .characterDao()
                .getAll()
                .let { characters -> CharacterMapper.mapDbModelToRpModelOverview(characters) }
                .let { characters -> RpModelResponse(characters.size, characters.size, characters) }
                .also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getStarredCharacter(id: Int): Flow<RpModelResponse<RpModelCharacterDetail>> {
        return flow {
            database
                .characterDao()
                .getCharacter(id)!!
                .let { character -> CharacterMapper.mapDbModelToRpModelDetail(character, true) }
                .let { character -> RpModelResponse(1, 1, character) }
                .also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    // ----------------------------------------------------------------------------

    suspend fun starCharacter(id: Int): Flow<Boolean> {
        return flow {
            apiRoutes
                .getCharacter(
                    "4005-$id",
                    mapOf("field_list" to "id,name,real_name,image,aliases,birth,gender,powers,origin")
                )
                .results
                .let { character -> CharacterMapper.mapApiModelDetailToDbModel(character) }
                .also { dbCharacter -> database.characterDao().insert(dbCharacter) }
            emit(true)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun unstarCharacter(id: Int): Flow<Boolean> {
        return flow {
            database
                .characterDao()
                .delete(id)
                .let { numberOfAffectedRows -> numberOfAffectedRows > 0 }
                .also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }
}