package com.alex.comicdiscovery.repository.character

import com.alex.comicdiscovery.repository.api.ApiClient
import com.alex.comicdiscovery.repository.database.ComicDiscoveryDatabase
import com.alex.comicdiscovery.repository.models.*
import com.alex.comicdiscovery.util.mapping.CharacterMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepository {

    suspend fun getCharacter(id: Int): Flow<RpModelResponse<RpModelCharacterDetail>> {
        return flow {
            ApiClient
                .routes
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
                            ComicDiscoveryDatabase.database.characterDao()
                                .getCharacter(id) != null
                        )
                    )
                }.also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    // ----------------------------------------------------------------------------

    suspend fun getStarredCharacters(): Flow<RpModelResponse<List<RpModelCharacterOverview>>> {
        return flow {
            ComicDiscoveryDatabase
                .database
                .characterDao()
                .getAll()
                .let { characters -> CharacterMapper.mapDbModelToRpModelOverview(characters) }
                .let { characters -> RpModelResponse(characters.size, characters.size, characters) }
                .also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getStarredCharacter(id: Int): Flow<RpModelResponse<RpModelCharacterDetail>> {
        return flow {
            ComicDiscoveryDatabase
                .database
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
            ApiClient
                .routes
                .getCharacter(
                    "4005-$id",
                    mapOf("field_list" to "id,name,real_name,image,aliases,birth,gender,powers,origin")
                )
                .results
                .let { character -> CharacterMapper.mapApiModelDetailToDbModel(character) }
                .also { dbCharacter -> ComicDiscoveryDatabase.database.characterDao().insert(dbCharacter) }
            emit(true)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun unstarCharacter(id: Int): Flow<Boolean> {
        return flow {
            ComicDiscoveryDatabase
                .database
                .characterDao()
                .delete(id)
                .let { numberOfAffectedRows -> numberOfAffectedRows > 0 }
                .also { emit(it) }
        }.flowOn(Dispatchers.IO)
    }
}