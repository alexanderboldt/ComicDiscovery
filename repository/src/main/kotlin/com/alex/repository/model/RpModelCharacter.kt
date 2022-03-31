package com.alex.repository.model

data class RpModelCharacter(
    val id: Int,
    val name: String,
    val summary: String,
    val realName: String?,
    val smallImageUrl: String,
    val gender: Int,
    val aliases: String?,
    val birth: String?,
    val powers: List<String>,
    val origin: String,
    val isStarred: Boolean
)