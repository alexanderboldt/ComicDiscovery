package com.alex.comicdiscovery.util.mapping

import com.alex.comicdiscovery.repository.datasource.api.models.ApiModelCharacterDetail
import com.alex.comicdiscovery.repository.datasource.api.models.ApiModelCharacterOverview
import com.alex.comicdiscovery.repository.datasource.database.character.DbModelCharacter
import com.alex.comicdiscovery.repository.models.RpModelCharacterDetail
import com.alex.comicdiscovery.repository.models.RpModelCharacterOverview

// region - from api to repository

fun List<ApiModelCharacterOverview>.toRpModel(): List<RpModelCharacterOverview> {
    return map {
        RpModelCharacterOverview(it.id, it.name, it.realName, it.image.smallUrl)
    }
}

fun ApiModelCharacterDetail.toRpModel(isStarred: Boolean): RpModelCharacterDetail {
    return RpModelCharacterDetail(
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
}

// endregion

// region - from api to database

fun ApiModelCharacterDetail.toDbModel(): DbModelCharacter {
    return DbModelCharacter(
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
}

// endregion

// region - from database to repository

fun List<DbModelCharacter>.toRpModelOverview(): List<RpModelCharacterOverview> {
    return map {
        RpModelCharacterOverview(
            it.id,
            it.name,
            it.realName,
            it.smallImageUrl
        )
    }
}

fun DbModelCharacter.toRpModelDetail(isStarred: Boolean): RpModelCharacterDetail {
    return RpModelCharacterDetail(
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
}

// endregion