package com.example.comicdiscovery.repository.character

import com.example.comicdiscovery.repository.api.ApiClient
import com.example.comicdiscovery.repository.models.*

class CharacterRepository {

    suspend fun getCharacter(id: String): Result<Response<CharacterDetail>> {
        return try {
            ApiClient
                .getInterface()
                .getCharacter(
                    id,
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
                            response.results.origin.name
                        )
                    )
                }.let { response -> Result.Success(response) }
        } catch (throwable: Throwable) {
            Result.Failure(throwable)
        }
    }
}