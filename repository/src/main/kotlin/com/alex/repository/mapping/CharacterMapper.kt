package com.alex.repository.mapping

import com.alex.api.model.ApiModelCharacter
import com.alex.api.model.ApiModelCharacterMinimal
import com.alex.database.character.DbModelCharacter
import com.alex.repository.model.RpModelCharacter
import com.alex.repository.model.RpModelCharacterMinimal

// region - from api to repository

internal fun List<ApiModelCharacterMinimal>.toRpModel() = map {
    RpModelCharacterMinimal(it.id, it.name, it.realName, it.image.smallUrl)
}

internal fun ApiModelCharacter.toRpModel(isStarred: Boolean) = RpModelCharacter(
    id,
    name,
    summary,
    realName,
    image.smallUrl,
    gender,
    aliases,
    birth,
    powers.map { it.name },
    origin.name,
    teams.map { it.name },
    friends.map { it.name },
    enemies.map { it.name },
    isStarred
)

// endregion

// region - from api to database

internal fun ApiModelCharacter.toDbModel() = DbModelCharacter(
    id,
    name,
    summary,
    realName,
    image.smallUrl,
    gender,
    aliases,
    birth,
    powers.map { it.name },
    origin.name,
    teams.map { it.name },
    friends.map { it.name },
    enemies.map { it.name }
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
    summary,
    realName,
    smallImageUrl,
    gender,
    aliases,
    birth,
    powers,
    origin,
    teams,
    friends,
    enemies,
    isStarred
)

// endregion