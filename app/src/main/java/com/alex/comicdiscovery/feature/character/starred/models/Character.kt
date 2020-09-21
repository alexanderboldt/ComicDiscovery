package com.alex.comicdiscovery.feature.character.starred.models

data class Character(
    val id: Int,
    val name: String,
    val realName: String?,
    val iconUrl: String)