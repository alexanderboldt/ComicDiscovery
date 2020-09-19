package com.alex.comicdiscovery.repository.character

import com.alex.comicdiscovery.repository.api.ApiClient
import com.alex.comicdiscovery.repository.api.models.Power
import com.alex.comicdiscovery.repository.database.ComicDiscoveryDatabase
import com.alex.comicdiscovery.repository.database.character.Character
import com.alex.comicdiscovery.repository.models.*

class CharacterRepository {

    suspend fun getCharacter(id: Int): Result<Response<CharacterDetail>> {
        return try {
            ApiClient
                .getInterface()
                .getCharacter(
                    "4005-$id",
                    mapOf("field_list" to "name,real_name,image,aliases,birth,gender,powers,origin"))
                .let { response ->
                    Response(
                        response.numberOfPageResults,
                        response.numberOfTotalResults,
                        CharacterDetail(
                            response.results.name,
                            response.results.realName,
                            Image(response.results.image.smallUrl),
                            response.results.gender,
                            response.results.aliases,
                            response.results.birth,
                            response.results.powers,
                            response.results.origin.name,
                            ComicDiscoveryDatabase.database.characterDao().getCharacter(id) != null
                        )
                    )
                }.let { response -> Result.Success(response) }
        } catch (throwable: Throwable) {
            Result.Failure(throwable)
        }
    }

    // ----------------------------------------------------------------------------

    suspend fun getStarredCharacters(): Result<Response<List<CharacterOverview>>> {
        return ComicDiscoveryDatabase
            .database
            .characterDao()
            .getAll()
            .map { character ->
                CharacterOverview(character.id, character.name, character.realName, Image(character.smallImageUrl))
            }.let { characters -> Response(characters.size, characters.size, characters) }
            .let { response -> Result.Success(response) }
    }

    suspend fun getStarredCharacter(id: Int): Result<Response<CharacterDetail>> {
        return ComicDiscoveryDatabase
            .database
            .characterDao()
            .getCharacter(id)!!
            .let { character ->
                CharacterDetail(
                    character.name,
                    character.realName,
                    Image(character.smallImageUrl),
                    character.gender,
                    character.aliases,
                    character.birth,
                    character.powers.split(";").map { Power(it) },
                    character.origin,
                    true,
                )
            }.let { character -> Response(1, 1, character) }
            .let { response -> Result.Success(response) }
    }

    // ----------------------------------------------------------------------------

    suspend fun starCharacter(id: Int): Boolean {
        return try {
            ApiClient
                .getInterface()
                .getCharacter(
                    "4005-$id",
                    mapOf("field_list" to "name,real_name,image,aliases,birth,gender,powers,origin"))
                .results
                .let { character ->
                    Character(
                        id,
                        character.name,
                        character.realName,
                        character.image.smallUrl,
                        character.gender,
                        character.aliases,
                        character.birth,
                        character.powers.joinToString(";"),
                        character.origin.name)
                }.also { dbCharacter ->
                    ComicDiscoveryDatabase.database.characterDao().insert(dbCharacter)
                }
            true
        } catch (throwable: Throwable) {
            false
        }
    }

    suspend fun unstarCharacter(id: Int): Boolean {
        return ComicDiscoveryDatabase
            .database
            .characterDao()
            .delete(id)
            .let { numberOfAffectedRows -> numberOfAffectedRows > 0 }
    }
}