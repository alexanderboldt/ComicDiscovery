package com.alex.comicdiscovery.feature.character.detail.model

data class UiModelCharacter(
    val imageUrl: String,
    val name: String,
    val realName: String?,
    val aliases: String,
    val gender: String,
    val birth: String,
    val powers: String,
    val origin: String,
)