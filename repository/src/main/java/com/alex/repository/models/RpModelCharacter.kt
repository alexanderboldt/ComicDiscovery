package com.alex.repository.models

data class RpModelCharacter(
    val id: Int,
    val name: String,
    val realName: String?,
    val smallImageUrl: String,
    val gender: Int,
    val aliases: String?,
    val birth: String?,
    val powers: List<String>,
    val origin: String,
    val isStarred: Boolean
)