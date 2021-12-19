package com.alex.repository.mapping

import com.alex.repository.datasource.api.models.ApiModelCharacter
import com.alex.repository.datasource.api.models.ApiModelCharacterMinimal
import com.alex.repository.datasource.database.character.DbModelCharacter
import com.alex.repository.models.RpModelCharacter
import com.alex.repository.models.RpModelCharacterMinimal

// region - from api to repository

internal fun List<ApiModelCharacterMinimal>.toRpModel() = map {
    RpModelCharacterMinimal(it.id, it.name, it.realName, it.image.smallUrl)
}

internal fun ApiModelCharacter.toRpModel(isStarred: Boolean) = RpModelCharacter(
    id,
    name,
    realName,
    image.smallUrl,
    gender,
    aliases,
    birth,
    powers.map { it.name },
    origin.name,
    isStarred
)

// endregion

// region - from api to database

internal fun ApiModelCharacter.toDbModel() = DbModelCharacter(
    id,
    name,
    realName,
    image.smallUrl,
    gender,
    aliases,
    birth,
    powers.map { it.name },
    origin.name
)

// endregion

// region - from database to repository

internal fun List<DbModelCharacter>.toRpModelMinimal() = map {
    RpModelCharacterMinimal(
        it.id,
        it.name,
        it.realName,
        it.smallImageUrl
    )
}

internal fun DbModelCharacter.toRpModel(isStarred: Boolean) = RpModelCharacter(
    id,
    name,
    realName,
    smallImageUrl,
    gender,
    aliases,
    birth,
    powers,
    origin,
    isStarred
)

// endregion