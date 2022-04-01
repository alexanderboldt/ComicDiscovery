package com.alex.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiModelCharacter(
    val id: Int,
    val name: String,
    @Json(name = "deck") val summary: String,
    @Json(name = "real_name") val realName: String?,
    val image: ApiModelImage,
    val gender: Int,
    val aliases: String?,
    val birth: String?,
    val powers: List<ApiModelPower>,
    val origin: ApiModelOrigin,
    val teams: List<ApiModelTeam>,
    @Json(name = "character_friends") val friends: List<ApiModelFriend>,
    @Json(name = "character_enemies") val enemies: List<ApiModelEnemy>,
)