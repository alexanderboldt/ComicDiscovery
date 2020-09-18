package com.alex.comicdiscovery.feature.character.overview.models

data class Character(
    val id: Int,
    val name: String,
    val realName: String?,
    val iconUrl: String)