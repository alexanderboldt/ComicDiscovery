package com.alex.comicdiscovery.repository.character

import com.alex.comicdiscovery.repository.api.ApiClient
import com.alex.comicdiscovery.repository.models.*
import kotlinx.coroutines.delay

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
                            id == 138508
                        // todo: check the database if the current character is stored
                        )
                    )
                }.let { response -> Result.Success(response) }
        } catch (throwable: Throwable) {
            Result.Failure(throwable)
        }
    }

    suspend fun starCharacter(id: Int): Boolean {
        delay(3000)
        // todo retrieve the character with the assigned id and store it in the database
        return true
    }

    suspend fun unstarCharacter(id: Int): Boolean {
        // remove the character with the assigned id from the database
        return true
    }
}