package com.alex.comicdiscovery.util.mapping

import com.alex.comicdiscovery.repository.api.models.ApiModelCharacterDetail
import com.alex.comicdiscovery.repository.api.models.ApiModelCharacterOverview
import com.alex.comicdiscovery.repository.database.character.DbModelCharacter
import com.alex.comicdiscovery.repository.models.RpModelCharacterDetail
import com.alex.comicdiscovery.repository.models.RpModelCharacterOverview

object CharacterMapper {

    // from api to repository

    fun mapApiModelOverviewToRpModelOverview(input: List<ApiModelCharacterOverview>): List<RpModelCharacterOverview> {
        return input.map { apiModelCharacterOverview ->
            mapApiModelOverviewToRpModelOverview(
                apiModelCharacterOverview
            )
        }
    }
    
    fun mapApiModelOverviewToRpModelOverview(input: ApiModelCharacterOverview): RpModelCharacterOverview {
        return RpModelCharacterOverview(
            input.id,
            input.name,
            input.realName,
            ImageMapper.mapApiModelToRpModel(input.image)
        )
    }

    fun mapApiModelDetailToRpModelDetail(input: ApiModelCharacterDetail, isStarred: Boolean): RpModelCharacterDetail {
        return RpModelCharacterDetail(
            input.id,
            input.name,
            input.realName,
            ImageMapper.mapApiModelToRpModel(input.image),
            input.gender,
            input.aliases,
            input.birth,
            input.powers.map { it.name },
            input.origin.name,
            isStarred
        )
    }

    // from database to repository

    fun mapDbModelToRpModelOverview(input: List<DbModelCharacter>): List<RpModelCharacterOverview> {
        return input.map { bbModelCharacter ->
            mapDbModelToRpModelOverview(
                bbModelCharacter
            )
        }
    }

    fun mapDbModelToRpModelOverview(input: DbModelCharacter): RpModelCharacterOverview {
        return RpModelCharacterOverview(input.id, input.name, input.realName,
            ImageMapper.mapStringToRpModel(input.smallImageUrl)
        )
    }

    fun mapDbModelToRpModelDetail(input: DbModelCharacter, isStarred: Boolean): RpModelCharacterDetail {
        return RpModelCharacterDetail(
            input.id,
            input.name,
            input.realName,
            ImageMapper.mapStringToRpModel(input.smallImageUrl),
            input.gender,
            input.aliases,
            input.birth,
            input.powers.split(";"),
            input.origin,
            isStarred
        )
    }
    
    // from api to database
    
    fun mapApiModelDetailToDbModel(input: ApiModelCharacterDetail): DbModelCharacter {
        return DbModelCharacter(
            input.id,
            input.name,
            input.realName,
            input.image.smallUrl,
            input.gender,
            input.aliases,
            input.birth,
            input.powers.joinToString(";"),
            input.origin.name)
    }
}