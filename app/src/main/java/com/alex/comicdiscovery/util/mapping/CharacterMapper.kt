package com.alex.comicdiscovery.util.mapping

import com.alex.comicdiscovery.repository.datasource.api.models.ApiModelCharacter
import com.alex.comicdiscovery.repository.datasource.api.models.ApiModelCharacterMinimal
import com.alex.comicdiscovery.repository.datasource.database.character.DbModelCharacter
import com.alex.comicdiscovery.repository.models.RpModelCharacter
import com.alex.comicdiscovery.repository.models.RpModelCharacterMinimal

// region - from api to repository

fun List<ApiModelCharacterMinimal>.toRpModel() = map {
    RpModelCharacterMinimal(it.id, it.name, it.realName, it.image.smallUrl)
}

fun ApiModelCharacter.toRpModel(isStarred: Boolean) = RpModelCharacter(
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

fun ApiModelCharacter.toDbModel() = DbModelCharacter(
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

fun List<DbModelCharacter>.toRpModelMinimal() = map {
    RpModelCharacterMinimal(
        it.id,
        it.name,
        it.realName,
        it.smallImageUrl
    )
}

fun DbModelCharacter.toRpModel(isStarred: Boolean) = RpModelCharacter(
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