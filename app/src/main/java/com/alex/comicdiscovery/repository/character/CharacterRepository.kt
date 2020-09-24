package com.alex.comicdiscovery.repository.character

import com.alex.comicdiscovery.repository.api.ApiClient
import com.alex.comicdiscovery.repository.database.ComicDiscoveryDatabase
import com.alex.comicdiscovery.repository.models.*
import com.alex.comicdiscovery.util.mapping.CharacterMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepository {

    suspend fun getCharacter(id: Int): RpModelResult<RpModelResponse<RpModelCharacterDetail>> {
        return withContext(Dispatchers.IO) {
            try {
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
                    }.let { response -> RpModelResult.Success(response) }
            } catch (throwable: Throwable) {
                RpModelResult.Failure<RpModelResponse<RpModelCharacterDetail>>(throwable)
            }
        }
    }

    // ----------------------------------------------------------------------------

    suspend fun getStarredCharacters(): RpModelResult<RpModelResponse<List<RpModelCharacterOverview>>> {
        return withContext(Dispatchers.IO) {
            ComicDiscoveryDatabase
                .database
                .characterDao()
                .getAll()
                .let { characters -> CharacterMapper.mapDbModelToRpModelOverview(characters) }
                .let { characters -> RpModelResponse(characters.size, characters.size, characters) }
                .let { response -> RpModelResult.Success(response) }
        }
    }

    suspend fun getStarredCharacter(id: Int): RpModelResult<RpModelResponse<RpModelCharacterDetail>> {
        return withContext(Dispatchers.IO) {
            ComicDiscoveryDatabase
                .database
                .characterDao()
                .getCharacter(id)!!
                .let { character -> CharacterMapper.mapDbModelToRpModelDetail(character, true) }
                .let { character -> RpModelResponse(1, 1, character) }
                .let { response -> RpModelResult.Success(response) }
        }
    }

    // ----------------------------------------------------------------------------

    suspend fun starCharacter(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                ApiClient
                    .routes
                    .getCharacter(
                        "4005-$id",
                        mapOf("field_list" to "id,name,real_name,image,aliases,birth,gender,powers,origin")
                    )
                    .results
                    .let { character -> CharacterMapper.mapApiModelDetailToDbModel(character) }
                    .also { dbCharacter ->
                        ComicDiscoveryDatabase.database.characterDao().insert(dbCharacter)
                    }
                true
            } catch (throwable: Throwable) {
                false
            }
        }
    }

    suspend fun unstarCharacter(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            ComicDiscoveryDatabase
                .database
                .characterDao()
                .delete(id)
                .let { numberOfAffectedRows -> numberOfAffectedRows > 0 }
        }
    }
}